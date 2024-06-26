package com.sh.shpay.domain.openbanking.openbanking.application;


import com.sh.shpay.domain.acconut.api.dto.req.deposit.DepositAccountInfoDto;
import com.sh.shpay.domain.acconut.api.dto.req.deposit.DepositRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.deposit.DepositRequestList;
import com.sh.shpay.domain.acconut.api.dto.req.deposit.DepositUserInputRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.deposit.DepositResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.withdraw.WithdrawAccountInfoDto;
import com.sh.shpay.domain.acconut.api.dto.req.*;
import com.sh.shpay.domain.acconut.api.dto.req.withdraw.WithdrawRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.withdraw.WithdrawUserInputRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.withdraw.WithdrawResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.TransactionListResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.req.AuthType;
import com.sh.shpay.domain.openbanking.openbanking.dto.req.OpenBankingBalanceRequestDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.req.OpenBankingCodeAuthorizationRequestDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.req.OpenBankingSearchAccountRequestDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.res.OpenBankingAccountListResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.res.OpenBankingBalanceResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.res.OpenBankingUserInfoResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUser2leggedTokenRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUserRefreshTokenRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUser3leggedTokenRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser2leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser3leggedTokenResponseDto;
import com.sh.shpay.domain.users.domain.Users;
import com.sh.shpay.domain.users.domain.repository.UsersRepository;
import com.sh.shpay.global.exception.CustomException;
import com.sh.shpay.global.exception.ErrorCode;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeaderDto;
import com.sh.shpay.global.resolver.token.two.Openbanking2LeggedTokenFromHeaderDto;
import com.sh.shpay.global.util.openbanking.OpenBankingApiClient;
import com.sh.shpay.global.util.openbanking.OpenBankingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OpenBankingService {

    private static final String INIT_CODE = "code";

    private static final String STATE = "12345678901234567890123456789012";
    private static final String ACCESS_TOKEN_3_LEGGED_GRANT_TYPE = "authorization_code";
    private static final String ACCESS_TOKEN_2_LEGGED_GRANT_TYPE = "client_credentials";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String SCOPE_3_LEGGED = "login inquiry transfer"; // 우선 code 받을 때 3가지로 설정 | cardinfo fintechinfo
    private static final String SCOPE_2_LEGGED = "oob"; // 입출금할 때 사용
    @Value("${openbanking.client-id}")
    private String clientId;
    @Value("${openbanking.client-secret}")
    private String clientSecret;
    @Value("${openbanking.bank-tran-id}")
    private String bankTranId;
    @Value("${openbanking.redirect-uri}")
    private String redirectUri;
    private final OpenBankingApiClient openBankingApiClient;
    private final UsersRepository usersRepository;

    private final OpenBankingUtil openBankingUtil;


    /**
     * 토큰 신청 페이지
     * 사용자 AuthCode 발급 요청 -> 사용자 토큰 발급 요청으로 넘어감(callback url)
     */
    @LogTrace
    public OpenBankingCodeAuthorizationRequestDto requestAuthorization(){

        OpenBankingCodeAuthorizationRequestDto openBankingCodeAuthorizationRequestDto = OpenBankingCodeAuthorizationRequestDto.builder()
                .response_type(INIT_CODE)
                .client_id(clientId)
                .redirect_uri(redirectUri)
                .scope(SCOPE_3_LEGGED)
                .state(STATE)
                .auth_type(AuthType.INIT.getAuthCode())
                .build();

        return openBankingCodeAuthorizationRequestDto;
    }


    /**
     * 사용자 토큰 발급 요청, 3-legged
     */
    @LogTrace
    public OpenBankingUser3leggedTokenResponseDto requestUser3leggedToken(OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto, String state){


        if (!state.equals(STATE)) { // 주의1
            throw new CustomException(ErrorCode.NotMatchStateException);
        }

        if(openBankingUserCodeRequestDto.getCode().isBlank()){
            throw new CustomException(ErrorCode.NotExistAuthCodeException);
        }

        OpenBankingUser3leggedTokenRequestDto openBankingUser3leggedTokenRequestDto = OpenBankingUser3leggedTokenRequestDto.builder()
                .code(openBankingUserCodeRequestDto.getCode())
                .client_id(clientId)
                .client_secret(clientSecret)
                .redirect_uri(redirectUri)
                .grant_type(ACCESS_TOKEN_3_LEGGED_GRANT_TYPE)
                .build();

        OpenBankingUser3leggedTokenResponseDto openBankingUser3leggedTokenResponseDto = openBankingApiClient.requestUser3leggedToken(openBankingUser3leggedTokenRequestDto);

        return openBankingUser3leggedTokenResponseDto;

    }

    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     */
    @LogTrace
    public OpenBankingUserRefreshTokenResponseDto refreshUserToken(Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto) {

        if (openbanking3LeggedTokenFromHeaderDto.getRefreshToken().isBlank() || openbanking3LeggedTokenFromHeaderDto.getRefreshToken() == null) {
            throw new CustomException(ErrorCode.NotExistRefreshTokenException);
        }

        OpenBankingUserRefreshTokenRequestDto openBankingUserRefreshTokenRequestDto = OpenBankingUserRefreshTokenRequestDto.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .scope(SCOPE_3_LEGGED)
                .refresh_token(openbanking3LeggedTokenFromHeaderDto.getRefreshToken())
                .grant_type(REFRESH_TOKEN_GRANT_TYPE)
                .build();

        OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto = openBankingApiClient.refreshUserToken(openBankingUserRefreshTokenRequestDto);

        return openBankingUserRefreshTokenResponseDto;
    }

    /**
     * 사용자 토큰 발급 요청, 2-legged
     *
     */
    @LogTrace
    public OpenBankingUser2leggedTokenResponseDto requestUser2leggedToken(){


        OpenBankingUser2leggedTokenRequestDto openBankingUser2leggedTokenRequestDto = OpenBankingUser2leggedTokenRequestDto.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .scope(SCOPE_2_LEGGED)
                .grant_type(ACCESS_TOKEN_2_LEGGED_GRANT_TYPE)
                .build();

        OpenBankingUser2leggedTokenResponseDto openBankingUser2leggedTokenResponseDto = openBankingApiClient.requestUser2leggedToken(openBankingUser2leggedTokenRequestDto);

        return openBankingUser2leggedTokenResponseDto;

    }


    /**
     * 사용자 정보 가져오기 - ci값
     */
    @LogTrace
    public OpenBankingUserInfoResponseDto requestUserInfo(Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto){

        Users users = usersRepository.findById(userInfoFromSessionDto.getUserId()).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        if(users == null){
            throw new CustomException(ErrorCode.NotExistUserException);
        }

        OpenBankingUserInfoResponseDto openBankingUserInfoResponseDto = openBankingApiClient.requestUserInfo(openbanking3LeggedTokenFromHeaderDto, users.getUserSeqNo());

        if(!users.hasOpenBankCi()){
            users.updateOpenBankCi(openBankingUserInfoResponseDto.getUser_ci());

        }

        return openBankingUserInfoResponseDto;
    }


    /**
     * 등록계좌조회
     */
    @LogTrace
    public OpenBankingAccountListResponseDto requestAccountList(AccountRequestDto accountRequestDto){

        OpenBankingSearchAccountRequestDto openBankingSearchAccountRequestDto = OpenBankingSearchAccountRequestDto.builder()
                .user_seq_no(accountRequestDto.getUserSeqNo())
                .accessToken(accountRequestDto.getAccessToken())
                .include_cancel_yn("Y")
                .sort_order("D")
                .build();

        OpenBankingAccountListResponseDto openBankingAccountListResponseDto = openBankingApiClient.requestAccountList(openBankingSearchAccountRequestDto);

        return openBankingAccountListResponseDto;

    }

    /**
     * 잔액조회
     *
     */
    @LogTrace
    public OpenBankingBalanceResponseDto requestBalance(BalanceRequestDto balanceRequestDto){

        OpenBankingBalanceRequestDto openBankingBalanceRequestDto = OpenBankingBalanceRequestDto.builder()
                .accessToken(balanceRequestDto.getAccessToken())
                .bank_tran_id(openBankingUtil.generateBankTranId(bankTranId + "U")) // tranId 생성
                .fintech_use_num(balanceRequestDto.getFintechUseNum())
                .tran_dtime(OpenBankingUtil.transTime())
                .build();

        OpenBankingBalanceResponseDto openBankingBalanceResponseDto = openBankingApiClient.requestBalance(openBankingBalanceRequestDto);

        return openBankingBalanceResponseDto;

    }

    /**
     * 거래내역조회
     */
    @LogTrace
    public TransactionListResponseDto requestTransactionList(Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto, String fintechUseNum){


        TransactionListRequestDto transactionListRequestDto = TransactionListRequestDto.builder()
                .bank_tran_id(openBankingUtil.generateBankTranId(bankTranId + "U"))
                .fintech_use_num(fintechUseNum)
                .inquiry_type("A")
                .inquiry_base("D")
                .from_date(OpenBankingUtil.transTime().substring(0, 8))
                .to_date(OpenBankingUtil.transTime().substring(0, 8))
                .sort_order("D")
                .build();

        TransactionListResponseDto transactionListResponseDto = openBankingApiClient.requestTransactionList(openbanking3LeggedTokenFromHeaderDto, transactionListRequestDto);

        return transactionListResponseDto;

    }



    /**
     * 출금이체(핀테크이용번호 사용)
     */
    @LogTrace
    public WithdrawResponseDto requestWithdraw(Openbanking2LeggedTokenFromHeaderDto openbanking2LeggedTokenFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto, WithdrawAccountInfoDto withdrawAccountInfoDto, WithdrawUserInputRequestDto withdrawUserInputRequestDto){

        WithdrawRequestDto withdrawRequestDto = WithdrawRequestDto.builder()
                .bank_tran_id(openBankingUtil.generateBankTranId(bankTranId + "U"))
                .cntr_account_type("N") // N : 계좌, C : 계정
                .cntr_account_num(withdrawAccountInfoDto.getAccount_num())
                .dps_print_content(withdrawUserInputRequestDto.getDps_print_content())
                .fintech_use_num(withdrawAccountInfoDto.getFintech_use_num()) //입금계좌
                .wd_print_content(withdrawUserInputRequestDto.getWd_print_content())
                .tran_amt(withdrawUserInputRequestDto.getTran_amt())
                .req_client_name(userInfoFromSessionDto.getName())
                .req_client_fintech_use_num(withdrawUserInputRequestDto.getFintech_use_num()) //출금계좌(내계좌)
                .req_client_num(userInfoFromSessionDto.getName()) //영어로
                .transfer_purpose("ST")
                .recv_client_name(withdrawUserInputRequestDto.getRecv_client_name())
                .recv_client_bank_code("097") // test : 097
                .recv_client_account_num(withdrawUserInputRequestDto.getRecv_client_account_num())
                .build();

        withdrawRequestDto.setTran_dtime(OpenBankingUtil.transTime());

        WithdrawResponseDto withdrawResponseDto = openBankingApiClient.requestWithdraw(openbanking2LeggedTokenFromHeaderDto.getAccessToken(), withdrawRequestDto);

        return withdrawResponseDto;

    }


    /**
     * 입급이체(핀테크이용번호 사용)
     */
    @LogTrace
    public DepositResponseDto requestDeposit(Openbanking2LeggedTokenFromHeaderDto openbanking2LeggedTokenFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto, DepositAccountInfoDto depositAccountInfoDto, DepositUserInputRequestDto depositUserInputRequestDto){

        ArrayList<DepositRequestList> depositRequestList = new ArrayList<>();

        DepositRequestList depositRequestListDto = DepositRequestList.builder()
                .tran_no("1") // 단건만 가능
                .fintech_use_num(depositAccountInfoDto.getFintech_use_num()) //출금계좌
                .print_content(depositUserInputRequestDto.getPrint_content())
                .tran_amt(depositUserInputRequestDto.getTran_amt())
                .req_client_name(depositUserInputRequestDto.getReq_client_name())
                .req_client_fintech_use_num(depositUserInputRequestDto.getFintech_use_num()) //입금계좌(내계좌)
                .req_client_num(depositUserInputRequestDto.getReq_client_num())
                .transfer_purpose("ST")
                .build();

        depositRequestList.add(depositRequestListDto);

        DepositRequestDto depositRequestDto = DepositRequestDto.builder()
                .cntr_account_type("N")
                .cntr_account_num(depositAccountInfoDto.getAccount_num())
                .wd_pass_phrase("NONE")
                .wd_print_content(depositUserInputRequestDto.getWd_print_content())
                .name_check_option("off")
                .req_cnt("1")
                .req_list(depositRequestList)
                .build();

        depositRequestDto.setTran_dtime(OpenBankingUtil.transTime());


        DepositResponseDto withdrawResponseDto = openBankingApiClient.requestDeposit(openbanking2LeggedTokenFromHeaderDto.getAccessToken(), depositRequestDto);

        return withdrawResponseDto;

    }







}
