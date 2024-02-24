package com.sh.shpay.domain.openbanking.openbanking.application;


import com.sh.shpay.domain.acconut.api.dto.WithdrawAccountInfoDto;
import com.sh.shpay.domain.acconut.api.dto.req.AccountRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.BalanceRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.TransactionListRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.WithdrawRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.TransactionListResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.req.*;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.*;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUserRefreshTokenRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUserTokenRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserTokenResponseDto;
import com.sh.shpay.domain.users.domain.Users;
import com.sh.shpay.domain.users.domain.repository.UsersRepository;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeaderDto;
import com.sh.shpay.global.util.openbanking.OpenBankingApiClient;
import com.sh.shpay.global.util.openbanking.OpenBankingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenBankingService {

    private static final String INIT_CODE = "code";
    private static final String STATE = "12345678901234567890123456789012";
    private static final String ACCESS_TOKEN_GRANT_TYPE = "authorization_code";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String SCOPE = "login inquiry transfer"; // 우선 code 받을 때 3가지로 설정 | cardinfo fintechinfo
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
     * 사용자 AuthCode 발급 요청 -> 사용자 토큰 발급 요청으로 넘어감(callback url)
     */
//    public void requestAuthorization(){
//
//        log.info("========== OpenBankingService | requestAuthorization ============");
//
//        OpenBankingCodeAuthorizationRequestDto openBankingCodeAuthorizationRequestDto = OpenBankingCodeAuthorizationRequestDto.builder()
//                .response_type(INIT_CODE)
//                .client_id(clientId)
//                .redirect_uri(redirectUri)
//                .scope(SCOPE)
//                .state(STATE)
//                .auth_type(AuthType.INIT.getAuthCode())
//                .build();
//
//        openBankingApiClient.requestAuthorization(openBankingCodeAuthorizationRequestDto);
//
//    }

    public OpenBankingCodeAuthorizationRequestDto requestAuthorization(){

        log.info("========== OpenBankingService | requestAuthorization ============");

        OpenBankingCodeAuthorizationRequestDto openBankingCodeAuthorizationRequestDto = OpenBankingCodeAuthorizationRequestDto.builder()
                .response_type(INIT_CODE)
                .client_id(clientId)
                .redirect_uri(redirectUri)
                .scope(SCOPE)
                .state(STATE)
                .auth_type(AuthType.INIT.getAuthCode())
                .build();

        return openBankingCodeAuthorizationRequestDto;
    }


    /**
     * 사용자 토큰 발급 요청, 3-legged
     */
    public OpenBankingUserTokenResponseDto requestUserToken(OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto, String state){

        log.info("========== OpenBankingService |  requestUserToken ============");

        if (state.equals(STATE)) { // 주의1
            throw new RuntimeException("State 값이 일치하지 않습니다.");
        }

        if(openBankingUserCodeRequestDto.getCode().isBlank()){
            log.error("code가 존재하지 않습니다.");

            return null;
        }

        OpenBankingUserTokenRequestDto openBankingUserTokenRequestDto = OpenBankingUserTokenRequestDto.builder()
                .code(openBankingUserCodeRequestDto.getCode())
                .client_id(clientId)
                .client_secret(clientSecret)
                .redirect_uri(redirectUri)
                .grant_type(ACCESS_TOKEN_GRANT_TYPE)
                .build();

        OpenBankingUserTokenResponseDto openBankingUserTokenResponseDto = openBankingApiClient.requestUserToken(openBankingUserTokenRequestDto);

        return openBankingUserTokenResponseDto;

    }

    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     */
    public OpenBankingUserRefreshTokenResponseDto refreshUserToken(TokenInfoFromHeaderDto tokenInfoFromHeaderDto) {

        if (tokenInfoFromHeaderDto.getRefreshToken().isBlank() || tokenInfoFromHeaderDto.getRefreshToken() == null) {
            log.error("refresh_token이 존재하지 않습니다.");

            return null;
        }

        OpenBankingUserRefreshTokenRequestDto openBankingUserRefreshTokenRequestDto = OpenBankingUserRefreshTokenRequestDto.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .scope(SCOPE)
                .refresh_token(tokenInfoFromHeaderDto.getRefreshToken())
                .grant_type(REFRESH_TOKEN_GRANT_TYPE)
                .build();

        OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto = openBankingApiClient.refreshUserToken(openBankingUserRefreshTokenRequestDto);

        return openBankingUserRefreshTokenResponseDto;
    }


    /**
     * 사용자 정보 가져오기 - ci값
     */
    public OpenBankingUserInfoResponseDto requestUserInfo(TokenInfoFromHeaderDto tokenInfoFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto){

        Users users = usersRepository.findById(userInfoFromSessionDto.getUserId()).orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));

        if(users == null){
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }

        OpenBankingUserInfoResponseDto openBankingUserInfoResponseDto = openBankingApiClient.requestUserInfo(tokenInfoFromHeaderDto, users.getUserSeqNo());

        if(!users.hasOpenBankCi()){
            users.updateOpenBankCi(openBankingUserInfoResponseDto.getUser_ci());

        }

        return openBankingUserInfoResponseDto;
    }


    /**
     * 등록계좌조회
     */
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
    public OpenBankingBalanceResponseDto requestBalance(BalanceRequestDto balanceRequestDto){

        String bank_tran_id = openBankingUtil.generateBankTranId(bankTranId + "U");




        OpenBankingBalanceRequestDto openBankingBalanceRequestDto = OpenBankingBalanceRequestDto.builder()
                .accessToken(balanceRequestDto.getAccessToken())
                .bank_tran_id(openBankingUtil.generateBankTranId(bankTranId + "U")) // tranId 생성하는데 -> 이거 redis에서 넣고 한번씩 쏴봐야할 것 같은데
                .fintech_use_num(balanceRequestDto.getFintechUseNum())
                .tran_dtime(OpenBankingUtil.transTime())
                .build();

        OpenBankingBalanceResponseDto openBankingBalanceResponseDto = openBankingApiClient.requestBalance(openBankingBalanceRequestDto);

        return openBankingBalanceResponseDto;

    }

    /**
     * 거래내역조회
     */
    public TransactionListResponseDto requestTransactionList(TokenInfoFromHeaderDto tokenInfoFromHeaderDto, String fintechUseNum){

        log.info("===== : " + OpenBankingUtil.transTime().substring(0, 8));

        TransactionListRequestDto transactionListRequestDto = TransactionListRequestDto.builder()
                .bank_tran_id(openBankingUtil.generateBankTranId(bankTranId + "U"))
                .fintech_use_num(fintechUseNum)
                .inquiry_type("A")
                .inquiry_base("D")
                .from_date(OpenBankingUtil.transTime().substring(0, 8))
                .to_date(OpenBankingUtil.transTime().substring(0, 8))
                .sort_order("D")
                .build();

        TransactionListResponseDto transactionListResponseDto = openBankingApiClient.requestTransactionList(tokenInfoFromHeaderDto, transactionListRequestDto);

        return transactionListResponseDto;

    }



    /**
     * 출금이체
     */
    public OpenBankingTransferResponseDto requestWithdraw(TokenInfoFromHeaderDto tokenInfoFromHeaderDto, UserInfoFromSessionDto userInfoFromSessionDto, WithdrawAccountInfoDto withdrawAccountInfoDto, WithdrawRequestDto withdrawRequestDto){

        OpenBankingTransferRequestDto openBankingTransferRequestDto = OpenBankingTransferRequestDto.builder()
                .bank_tran_id(openBankingUtil.generateBankTranId(bankTranId + "U"))
                .cntr_account_type("N") // N : 계좌, C : 계정
                .cntr_account_num(withdrawAccountInfoDto.getAccount_num())
                .dps_print_content(withdrawRequestDto.getDps_print_content())
                .fintech_use_num(withdrawAccountInfoDto.getFintech_use_num())
                .wd_print_content(withdrawRequestDto.getWd_print_content())
                .tran_amt(withdrawRequestDto.getTran_amt())
                .req_client_name(userInfoFromSessionDto.getName())
                .req_client_fintech_use_num(withdrawAccountInfoDto.getFintech_use_num())
                .req_client_num(userInfoFromSessionDto.getName()) //영어로해야하는데
                .transfer_purpose("ST")
                .recv_client_name(withdrawRequestDto.getRecv_client_name())
                .recv_client_bank_code("097") // test : 097
                .recv_client_account_num(withdrawRequestDto.getRecv_client_account_num())
                .build();

        openBankingTransferRequestDto.setTran_dtime(OpenBankingUtil.transTime());

        OpenBankingTransferResponseDto openBankingTransferResponseDto = openBankingApiClient.requestWithdraw(tokenInfoFromHeaderDto.getAccessToken(), openBankingTransferRequestDto);

        return openBankingTransferResponseDto;
    }







}
