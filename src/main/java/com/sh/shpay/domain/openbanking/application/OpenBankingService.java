package com.sh.shpay.domain.openbanking.application;


import com.sh.shpay.domain.acconut.api.dto.req.AccountRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.BalanceRequestDto;
import com.sh.shpay.domain.openbanking.api.dto.req.*;
import com.sh.shpay.domain.openbanking.api.dto.res.*;
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

//    private String code = ""; // ars인증까지 하면 넘어옴 그 때 저장 / code 있나 확인
    private static final String ACCESS_TOKEN_GRANT_TYPE = "authorization_code";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    private static final String REFRESH_TOKEN_SCOPE = "login inquiry transfer"; // 우선 code 받을 때 3가지로 설정 | cardinfo fintechinfo
    @Value("${openbanking.client-id}")
    private String clientId;
    @Value("${openbanking.client-secret}")
    private String clientSecret;
    @Value("${openbanking.redirect-uri}")
    private String redirectUri;
    private final OpenBankingApiClient openBankingApiClient;

    /**
     * 사용자 토큰 발급 요청, 3-legged
     */
    public OpenBankingUserTokenResponseDto requestUserToken(OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto){

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
                .scope(REFRESH_TOKEN_SCOPE)
                .refresh_token(tokenInfoFromHeaderDto.getRefreshToken())
                .grant_type(REFRESH_TOKEN_GRANT_TYPE)
                .build();

        OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto = openBankingApiClient.refreshUserToken(openBankingUserRefreshTokenRequestDto);


        return openBankingUserRefreshTokenResponseDto;
    }


    /**
     * 계좌조회
     */
    public OpenBankingSearchAccountResponseDto requestAccountList(AccountRequestDto accountRequestDto){

        OpenBankingSearchAccountRequestDto openBankingSearchAccountRequestDto = OpenBankingSearchAccountRequestDto.builder()
                .user_seq_no(accountRequestDto.getUserSeqNo())
                .accessToken(accountRequestDto.getAccessToken())
                .include_cancel_yn("N")
                .sort_order("Y")
                .build();

        OpenBankingSearchAccountResponseDto openBankingSearchAccountResponseDto = openBankingApiClient.requestAccountList(openBankingSearchAccountRequestDto);

        return openBankingSearchAccountResponseDto;

    }

    /**
     * 잔액조회
     *
     */
    public OpenBankingBalanceResponseDto requestBalance(BalanceRequestDto balanceRequestDto){

        OpenBankingBalanceRequestDto openBankingBalanceRequestDto = OpenBankingBalanceRequestDto.builder()
                .accessToken(balanceRequestDto.getAccessToken())
                .bank_tran_id(OpenBankingUtil.generateBankTranId(balanceRequestDto.getCode() + "U"))
                .fintech_use_num(balanceRequestDto.getFintechUseNum())
                .tran_dtime(OpenBankingUtil.transTime())
                .build();

        OpenBankingBalanceResponseDto openBankingBalanceResponseDto = openBankingApiClient.requestBalance(openBankingBalanceRequestDto);

        return openBankingBalanceResponseDto;

    }

    /**
     * 출금이체
     */
    public OpenBankingTransferResponseDto requestTransfer(String accessToken, OpenBankingTransferRequestDto openBankingTransferRequestDto){

        OpenBankingTransferResponseDto openBankingTransferResponseDto = openBankingApiClient.requestTransfer(accessToken, openBankingTransferRequestDto);

        return openBankingTransferResponseDto;
    }

    /**
     * 사용자 정보 가져오기 - ci값
     */
    public OpenBankingUserInfoResponseDto requestUserInfo(OpenBankingUserInfoRequestDto openBankingUserInfoRequestDto){
        OpenBankingUserInfoResponseDto openBankingUserInfoResponseDto = openBankingApiClient.requestUserInfo(openBankingUserInfoRequestDto);

        return openBankingUserInfoResponseDto;
    }



}
