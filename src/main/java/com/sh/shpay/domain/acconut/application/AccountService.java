package com.sh.shpay.domain.acconut.application;

import com.sh.shpay.domain.acconut.api.dto.UserAccountDto;
import com.sh.shpay.domain.acconut.api.dto.WithdrawAccountInfoDto;
import com.sh.shpay.domain.acconut.api.dto.req.AccountRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.BalanceRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.WithdrawRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.AccountListResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.TransactionListResponseDto;
import com.sh.shpay.domain.acconut.domain.Account;
import com.sh.shpay.domain.acconut.domain.AccountType;
import com.sh.shpay.domain.acconut.domain.repository.AccountQueryRepositoryImpl;
import com.sh.shpay.domain.acconut.domain.repository.AccountRepository;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingBalanceResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingAccountListResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingTransferResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.users.domain.Users;
import com.sh.shpay.domain.users.domain.repository.UsersRepository;
import com.sh.shpay.global.exception.CustomException;
import com.sh.shpay.global.exception.ErrorCode;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.OpenbankingTokenInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final UsersRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountQueryRepositoryImpl accountQueryRepository;
    private final OpenBankingService openBankService;


    /**
     *  DB에서 계좌 조회 & 오픈뱅킹 API로 잔액조회
     */
    @LogTrace
    public AccountListResponseDto requestAccountList(OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto){

        Users users = userRepository.findById(userInfoFromSessionDto.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NotExistUserException));

        List<Account> accountList = accountRepository.findByUsers(users);

        if(accountList.isEmpty()){
            throw new CustomException(ErrorCode.NotExistAccountException);
        }

        //계좌 수 만큼 비동기 요청
        List<UserAccountDto> userAccountDtoList = accountList.stream()
                .map(account -> CompletableFuture.supplyAsync(() -> {
                            String balanceAmt = getBalanceAmt(account.getFintechUseNum(), // Openbanking api 잔액조회
                                    openbankingTokenInfoFromHeaderDto.getAccessToken(),
                                    users.getUserId());

                            UserAccountDto userAccountDto = UserAccountDto.builder()
                                    .userAccountId(account.getAccountId())
                                    .balanceAmt(balanceAmt)
                                    .accountNum(account.getAccountNum())
                                    .accountSeq(account.getAccountSeq())
                                    .bankCode(account.getBankCode())
                                    .fintechUseNum(account.getFintechUseNum())
                                    .bankName(account.getBankName())
                                    .userId(users.getUserId())
                                    .build();
                            return userAccountDto;

                        }, getAppropriateThreadPool(accountList.size()))
                        .orTimeout(10, TimeUnit.SECONDS) // 10초 지정
                        .handle((result,ex) -> {
                            if(ex == null){
                                return result;
                            }
                            else{
                                throw new CustomException(ErrorCode.FailToAccessBalanceAmount);
                            }
                        })
                )
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .sorted(Comparator.comparing(UserAccountDto::getUserAccountId).reversed()) //최근 계좌 순으로 정렬
                .collect(Collectors.toList());

        return new AccountListResponseDto(userAccountDtoList);
    }


    /**
     *  DB에서 특정은행계좌 조회 & 오픈뱅킹 API로 잔액조회
     */
    @LogTrace
    public AccountListResponseDto requestSpecificAccountList(OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto,
                                                     UserInfoFromSessionDto userInfoFromSessionDto,
                                                     String bankName){

        log.info("=== bankname : " + bankName);

        Users users = userRepository.findById(userInfoFromSessionDto.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NotExistUserException));

        List<Account> specificBankAccountList = accountQueryRepository.findSpecificBankAccount(users.getUserId(), bankName);

        if(specificBankAccountList.isEmpty()){
            throw new CustomException(ErrorCode.NotExistAccountException);
        }

        //계좌 수 만큼 비동기 요청
        List<UserAccountDto> userAccountDtoList = specificBankAccountList.stream()
                .map(account -> CompletableFuture.supplyAsync(() -> {
                                    String balanceAmt = getBalanceAmt(account.getFintechUseNum(), // Openbanking api 잔액조회
                                            openbankingTokenInfoFromHeaderDto.getAccessToken(),
                                            users.getUserId());

                                    UserAccountDto userAccountDto = UserAccountDto.builder()
                                            .userAccountId(account.getAccountId())
                                            .balanceAmt(balanceAmt)
                                            .accountNum(account.getAccountNum())
                                            .accountSeq(account.getAccountSeq())
                                            .bankCode(account.getBankCode())
                                            .fintechUseNum(account.getFintechUseNum())
                                            .bankName(account.getBankName())
                                            .userId(users.getUserId())
                                            .build();
                                    return userAccountDto;

                                }, getAppropriateThreadPool(specificBankAccountList.size()))
                                .orTimeout(10, TimeUnit.SECONDS) // 10초 지정
                                .handle((result,ex) -> {
                                    if(ex == null){
                                        return result;
                                    }
                                    else{
                                        throw new CustomException(ErrorCode.FailToAccessBalanceAmount);
                                    }
                                })
                )
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .sorted(Comparator.comparing(UserAccountDto::getUserAccountId).reversed()) //최근 계좌 순으로 정렬
                .collect(Collectors.toList());

        return new AccountListResponseDto(userAccountDtoList);
    }

    /**
     * 계좌 저장
     */
    @LogTrace
    public Long saveAccountList(OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto){

        Users users = userRepository.findById(userInfoFromSessionDto.getUserId()).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .accessToken(openbankingTokenInfoFromHeaderDto.getAccessToken())
                .userSeqNo(users.getUserSeqNo())
                .build();

        OpenBankingAccountListResponseDto openBankingAccountListResponseDto = openBankService.requestAccountList(accountRequestDto);

        // DB 조회
        List<Account> userAllAccountList = accountRepository.findByUsers(users);

        // key : fintechUseNum, value : fintechUseNum
        HashMap<String, String> fintechUseNumMap = getFintechUseNum(userAllAccountList);


        // 사용자 계좌 리스트(res_list)에서 이미 db에 있는 계좌들 빼고 새로운 계좌 필터링 후 저장
//        openBankingSearchAccountResponseDto.getRes_list().parallelStream()
        List<Account> filterAccountList = openBankingAccountListResponseDto.getRes_list().stream()
                .filter(openBankingAccountDto -> existFintechUseNum(fintechUseNumMap, openBankingAccountDto.getFintech_use_num())) // 알아서 순회함
                .map(resultAccount -> Account.createAccount(
                                resultAccount.getFintech_use_num(),
                                resultAccount.getBank_name(),
                                resultAccount.getAccount_num_masked(), // 마스킹 되서 나옴 -> 계좌번호는 특정 자격요건을 갖춘 이용기관에 선별적 제공 따라서 우선  마스킹된 계좌번호 제공
                                resultAccount.getBank_code_std(), // 카드사 대표 코드(금공기관 공동코드)
                                resultAccount.getAccount_seq(),
                                AccountType.SUB, // 우선 SUB으로
                                resultAccount.getAccount_holder_name(),
                                users
                        )
                )
                .collect(Collectors.toList());

        if(filterAccountList.isEmpty()) return 0L;


        accountRepository.saveAll(filterAccountList); // 새로운 계좌들 전부 저장

        return Long.valueOf(filterAccountList.size()); // 저장한 계좌 개수 리턴
    }



    /**
     * 주계좌 설정
     */
    @LogTrace
    public void updateAccountType(UserInfoFromSessionDto userInfoFromSessionDto, Long accountId){

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("해당 계좌가 존재하지 않습니다."));

        if(account.isMainAccount()){
            throw new CustomException(ErrorCode.AlreadySaveAccountTypeException);
        }

        Users users = userRepository.findById(userInfoFromSessionDto.getUserId()).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));



        Optional<Account> lastMainAccount = accountQueryRepository.findMainAccount(users.getUserId(), AccountType.MAIN);

        if(lastMainAccount.isEmpty()){ // 주계좌가 없다면 조회한 계좌를 주계좌로 변경
            account.updateAccountType(AccountType.MAIN);
        }
        else{ // 있다면 기존 주계좌를 부계좌로 변경 후 조회한 계좌를 주계좌로 변경
            lastMainAccount.get().updateAccountType(AccountType.SUB);
            account.updateAccountType(AccountType.MAIN);
        }

    }


    /**
     * 잔액조회
     *
     */
    @LogTrace
    private String getBalanceAmt(String fintechUseNum, String accessToken, Long userId){
        String balanceAmt = "";

        try {
            BalanceRequestDto balanceRequestDto = BalanceRequestDto.builder()
                    .accessToken(accessToken)
                    .fintechUseNum(fintechUseNum)
                    .userId(userId)
                    .build();

            OpenBankingBalanceResponseDto openBankingBalanceResponseDto = openBankService.requestBalance(balanceRequestDto); // 잔액조회 여기서 비동기 통신

            return openBankingBalanceResponseDto.getBalance_amt();

        } catch (Exception e) {
            throw new CustomException(ErrorCode.InternalServerException);
        }

    }

    /**
     * 거래내역조회
     */
    @LogTrace
    public TransactionListResponseDto requestTransactionList(OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto, Long accountId){

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new CustomException(ErrorCode.NotExistAccountException));

        TransactionListResponseDto transactionListResponseDto = openBankService.requestTransactionList(openbankingTokenInfoFromHeaderDto, account.getFintechUseNum());

        return transactionListResponseDto;

    }


    /**
     * 출금이제
     */
    @LogTrace
    public OpenBankingTransferResponseDto requestWithdraw(OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto, Long accountId, WithdrawRequestDto withdrawRequestDto){


        Account account = accountRepository.findById(accountId).orElseThrow(() -> new CustomException(ErrorCode.NotExistAccountException));

        WithdrawAccountInfoDto withdrawAccountInfoDto = new WithdrawAccountInfoDto(account.getAccountNum(), account.getFintechUseNum());

        OpenBankingTransferResponseDto openBankingTransferResponseDto = openBankService.requestWithdraw(openbankingTokenInfoFromHeaderDto, userInfoFromSessionDto, withdrawAccountInfoDto, withdrawRequestDto);

        return openBankingTransferResponseDto;

    }



    // =================

    /**
     * 비동기 쓰레드 개수 설정
     */
    private ExecutorService getAppropriateThreadPool(int size){
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(size, 100),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                });

        return executorService;
    }

    /**
     * fintechUseNum 추출
     */
    private HashMap<String, String> getFintechUseNum(List<Account> accountList){
        if(accountList.size() == 0) return new HashMap<>();

//        accountList.parallelStream() // 병철처리는 오히려 오버헤드 불러올 수 있음
        return accountList.stream()
                .collect(Collectors.toMap(
                        account -> account.getFintechUseNum(), account -> account.getFintechUseNum(),
                        (key, newValue) -> newValue, // 중복시 newValue로 덮어씌움
                        HashMap::new));
    }

    /**
     * 기존 계좌를 통해 fintechUseNum 존재하는지 확인
     */
    private boolean existFintechUseNum(HashMap<String, String> fintechUseNumMap, String fintechUseNum){

        return !fintechUseNumMap.containsKey(fintechUseNum);
    }

}
