package com.sh.shpay.domain.acconut.application;

import com.sh.shpay.domain.acconut.api.dto.UserAccountDto;
import com.sh.shpay.domain.acconut.api.dto.WithdrawAccountInfoDto;
import com.sh.shpay.domain.acconut.api.dto.req.AccountRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.BalanceRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.WithdrawRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.TransactionListResponseDto;
import com.sh.shpay.domain.acconut.domain.Account;
import com.sh.shpay.domain.acconut.domain.AccountType;
import com.sh.shpay.domain.acconut.domain.repository.AccountRepository;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.req.OpenBankingTransferRequestDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingBalanceResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingSearchAccountResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingTransferResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.token.domain.OpenBankingToken;
import com.sh.shpay.domain.openbanking.token.domain.repository.OpenBankingTokenQueryRepositoryImpl;
import com.sh.shpay.domain.openbanking.token.domain.repository.OpenBankingTokenRepository;
import com.sh.shpay.domain.users.domain.Users;
import com.sh.shpay.domain.users.domain.repository.UsersRepository;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeaderDto;
import com.sh.shpay.global.util.openbanking.OpenBankingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final UsersRepository userRepository;
    private final AccountRepository accountRepository;
    private final OpenBankingTokenRepository openBankingTokenRepository; //token, openbanking 합치기
    private final OpenBankingTokenQueryRepositoryImpl openBankingTokenQueryRepository;
    private final OpenBankingService openBankService;


    /**
     *  DB에서 계좌 조회 --> 오픈뱅킹 API로 잔액조회
     */
    public List<UserAccountDto> requestAccountList(TokenInfoFromHeaderDto tokenInfoFromHeaderDto){

        Users users = openBankingTokenQueryRepository.findUsersByAccessToken(tokenInfoFromHeaderDto.getAccessToken()).orElseThrow(() -> new RuntimeException("access_token이 존재하지 않습니다."));

        if(users == null){
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }


        List<Account> accountList = accountRepository.findByUsers(users);

        if(accountList.isEmpty()){
            throw new RuntimeException("유저의 계좌가 존재하지 않습니다.");
        }

        //계좌 수 만큼 비동기 요청
        List<UserAccountDto> userAccountDtoList = accountList.stream()
                .map(account -> CompletableFuture.supplyAsync(() -> {
                            String balanceAmt = getBalanceAmt(account.getFintechUseNum(), // 잔액조회
                                    tokenInfoFromHeaderDto.getAccessToken(),
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
                )
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .sorted(Comparator.comparing(UserAccountDto::getUserAccountId).reversed()) //최근 계좌 순으로 정렬
                .collect(Collectors.toList());


        return userAccountDtoList;
    }

    /**
     * 계좌 저장
     */
    public Long saveAccountList(TokenInfoFromHeaderDto tokenInfoFromHeaderDto){


        Users users = openBankingTokenQueryRepository.findUsersByAccessToken(tokenInfoFromHeaderDto.getAccessToken()).orElseThrow(() -> new RuntimeException("access_token이 존재하지 않습니다."));

        log.info("++ : " + users.getUserId());
        log.info("++ : " + users.getUserSeqNo());

        if(users == null){
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }


        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .accessToken(tokenInfoFromHeaderDto.getAccessToken())
                .userSeqNo(users.getUserSeqNo())
                .build();

        OpenBankingSearchAccountResponseDto openBankingSearchAccountResponseDto = openBankService.requestAccountList(accountRequestDto);

        log.info("==== : " + openBankingSearchAccountResponseDto.getUser_name());
        log.info("==== : " + openBankingSearchAccountResponseDto.getRes_list().get(0).getFintech_use_num());
        log.info("==== : " + openBankingSearchAccountResponseDto.getRes_cnt());
        log.info("==== : " + openBankingSearchAccountResponseDto.getRes_list().size());

        // DB 조회
        List<Account> userAllAccountList = accountRepository.findByUsers(users);

        // key : fintechUseNum, value : fintechUseNum
        HashMap<String, String> fintechUseNumMap = getFintechUseNum(userAllAccountList);


        // 사용자 계좌 리스트(res_list)에서 이미 db에 있는 계좌들 빼고 새로운 계좌 필터링
//        openBankingSearchAccountResponseDto.getRes_list().parallelStream()
        List<Account> filterAccountList = openBankingSearchAccountResponseDto.getRes_list().stream()
                .filter(openBankingAccountDto -> existFintechUseNum(fintechUseNumMap, openBankingAccountDto.getFintech_use_num())) // 알아서 순회함
                .map(resultAccount -> Account.createAccount(
                                resultAccount.getFintech_use_num(),
                                resultAccount.getBank_name(),
                                resultAccount.getAccount_num(), // 마스킹 되서 나옴 -> 계좌번호는 특정 자격요건을 갖춘 이용기관에 선별적 제공
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
    public void updateAccountType(TokenInfoFromHeaderDto tokenInfoFromHeaderDto, Long accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("해당 계좌가 존재하지 않습니다."));

        if(account.isMainAccount()){
            throw new RuntimeException("이미 주계좌로 설정되어 있습니다.");
        }

        Users users = openBankingTokenQueryRepository.findUsersByAccessToken(tokenInfoFromHeaderDto.getAccessToken()).orElseThrow(() -> new RuntimeException("access_token이 존재하지 않습니다."));

        if(users == null){
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }

        Optional<Account> lastMainAccount = accountRepository.findMainAccountByUsers(users);

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
    private String getBalanceAmt(String fintechUseNum, String accessToken, Long userId){
        String balanceAmt = "";

        try {
            BalanceRequestDto balanceRequestDto = BalanceRequestDto.builder()
                    .accessToken(accessToken)
                    .fintechUseNum(fintechUseNum)
                    .userId(userId)
                    .build();

            OpenBankingBalanceResponseDto openBankingBalanceResponseDto = openBankService.requestBalance(balanceRequestDto); // 잔액조회 여기서 비동기 통신

            log.info("----- : " + openBankingBalanceResponseDto.getFintech_use_num());
            log.info("----- : " + openBankingBalanceResponseDto.getBalance_amt());
            return openBankingBalanceResponseDto.getBalance_amt();
        } catch (Exception e) {
//            throw new RuntimeException(e);
            log.error("error message : " + e.getMessage());
        }

        return balanceAmt;
    }

    /**
     * 거래내역조회
     */
    public TransactionListResponseDto requestTransactionList(TokenInfoFromHeaderDto tokenInfoFromHeaderDto, Long accountId){

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("계좌가 존재하지 않습니다."));

        TransactionListResponseDto transactionListResponseDto = openBankService.requestTransactionList(tokenInfoFromHeaderDto, account.getFintechUseNum());

        return transactionListResponseDto;

    }


    /**
     * 출금이제
     */
    public OpenBankingTransferResponseDto requestWithdraw(TokenInfoFromHeaderDto tokenInfoFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto, Long accountId, WithdrawRequestDto withdrawRequestDto){


        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("계좌가 존재하지 않습니다."));

        WithdrawAccountInfoDto withdrawAccountInfoDto = new WithdrawAccountInfoDto(account.getAccountNum(), account.getFintechUseNum());


        OpenBankingTransferResponseDto openBankingTransferResponseDto = openBankService.requestWithdraw(tokenInfoFromHeaderDto, userInfoFromSessionDto, withdrawAccountInfoDto, withdrawRequestDto);

        return openBankingTransferResponseDto;

    }



    /**
     * 사용자 정보(ci, 계좌 리스트) 조회
     */

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
