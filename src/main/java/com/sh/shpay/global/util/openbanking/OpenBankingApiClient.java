package com.sh.shpay.global.util.openbanking;

import com.sh.shpay.domain.acconut.api.dto.req.deposit.DepositRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.withdraw.WithdrawRequestDto;
import com.sh.shpay.domain.acconut.api.dto.req.TransactionListRequestDto;
import com.sh.shpay.domain.acconut.api.dto.res.deposit.DepositResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.withdraw.WithdrawResponseDto;
import com.sh.shpay.domain.acconut.api.dto.res.TransactionListResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.req.OpenBankingBalanceRequestDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.req.OpenBankingSearchAccountRequestDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.res.OpenBankingAccountListResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.res.OpenBankingBalanceResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.dto.res.OpenBankingUserInfoResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUser2leggedTokenRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUserRefreshTokenRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUser3leggedTokenRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser2leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser3leggedTokenResponseDto;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@RequiredArgsConstructor
@Service
public class OpenBankingApiClient {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://testapi.openbanking.or.kr";
    private static final String SUCCESS_CODE = "A0000"; // 나중에 수정?


//    /**
//     * 사용자 AuthCode 발급 요청 -> 사용자 토큰 발급 요청으로 넘어감(callback url)
//     */
//    public void requestAuthorization(OpenBankingCodeAuthorizationRequestDto openBankingCodeAuthorizationRequestDto) {
//
//        String url = BASE_URL + "/oauth/2.0/authorize";
//
//        HttpEntity httpEntity = generateHttpEntity(generateHeader("Authorization", null));
//
//        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
//                .queryParam("response_type", openBankingCodeAuthorizationRequestDto.getResponse_type())
//                .queryParam("client_id", openBankingCodeAuthorizationRequestDto.getClient_id())
//                .queryParam("redirect_uri", openBankingCodeAuthorizationRequestDto.getRedirect_uri())
//                .queryParam("scope", openBankingCodeAuthorizationRequestDto.getScope())
//                .queryParam("state", openBankingCodeAuthorizationRequestDto.getState())
//                .queryParam("auth_type", openBankingCodeAuthorizationRequestDto.getAuth_type())
//                .build();
//
////        restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, OpenBankingSearchAccountResponseDto.class);
//
//
//        String result = restTemplate.getForObject(builder.toUriString(), String.class);
//        log.info("result : " + result);
//
//    }


    /**
     * 사용자 토큰 발급 요청, 3-legged
     */
    public OpenBankingUser3leggedTokenResponseDto requestUser3leggedToken(OpenBankingUser3leggedTokenRequestDto openBankingUserRequestToken){

        HttpHeaders httpHeaders = generateHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        HttpEntity httpEntity = generateHttpEntityWithBody(httpHeaders, openBankingUserRequestToken.toMultiValueMap());

        OpenBankingUser3leggedTokenResponseDto openBankingUserResponseToken = restTemplate.exchange(BASE_URL + "/oauth/2.0/token", HttpMethod.POST, httpEntity, OpenBankingUser3leggedTokenResponseDto.class).getBody();


        if(!isCodeValid(openBankingUserResponseToken.getRsp_code())){
            log.error("error code : {}, error msg : {}", openBankingUserResponseToken.getRsp_code(), openBankingUserResponseToken.getRsp_message());
            throw new RuntimeException(openBankingUserResponseToken.getRsp_message());
        }

        return openBankingUserResponseToken;

    }


    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     */
    public OpenBankingUserRefreshTokenResponseDto refreshUserToken(OpenBankingUserRefreshTokenRequestDto openBankingUserRefreshTokenRequestDto) {

        String url = BASE_URL + "/oauth/2.0/token";
        HttpHeaders httpHeaders = generateHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        HttpEntity httpEntity = generateHttpEntityWithBody(httpHeaders, openBankingUserRefreshTokenRequestDto.toMultiValueMap());

        OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto = restTemplate.exchange(url, HttpMethod.POST, httpEntity, OpenBankingUserRefreshTokenResponseDto.class).getBody();

        if(openBankingUserRefreshTokenResponseDto == null){
            log.error("token 갱신 실패");
            throw new RuntimeException("token 갱신 실패");
        }

        return openBankingUserRefreshTokenResponseDto;

    }

    /**
     * 사용자 토큰 발급 요청, 2-legged
     */
    public OpenBankingUser2leggedTokenResponseDto requestUser2leggedToken(OpenBankingUser2leggedTokenRequestDto openBankingUserRequestToken){

        HttpHeaders httpHeaders = generateHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        HttpEntity httpEntity = generateHttpEntityWithBody(httpHeaders, openBankingUserRequestToken.toMultiValueMap());

        OpenBankingUser2leggedTokenResponseDto openBankingUser2leggedTokenResponseDto = restTemplate.exchange(BASE_URL + "/oauth/2.0/token", HttpMethod.POST, httpEntity, OpenBankingUser2leggedTokenResponseDto.class).getBody();

        return openBankingUser2leggedTokenResponseDto;

    }


    /**
     * 사용자 정보 가져오기 - ci값
     * 계좌 정보도 다 나오는데 흠흠
     *
     */
    public OpenBankingUserInfoResponseDto requestUserInfo(Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto, String userSeqNo) {
        String url = BASE_URL + "/v2.0/user/me";

        HttpEntity httpEntity = generateHttpEntity(generateHeader("Authorization", openbanking3LeggedTokenFromHeaderDto.getAccessToken()));

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("user_seq_no", userSeqNo)
                .build();

        OpenBankingUserInfoResponseDto openBankingUserInfoResponseDto = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, OpenBankingUserInfoResponseDto.class).getBody();

        if(!isCodeValid(openBankingUserInfoResponseDto.getRsp_code())){
            log.error("error code : {}, error msg : {}", openBankingUserInfoResponseDto.getRsp_code(), openBankingUserInfoResponseDto.getRsp_message());
            throw new RuntimeException(openBankingUserInfoResponseDto.getRsp_message());
        }

        return openBankingUserInfoResponseDto;

    }



    /**
     * 등록계좌조회
     * AccountService
     * - 계좌 저장에서 쓰임
     */
    public OpenBankingAccountListResponseDto requestAccountList(OpenBankingSearchAccountRequestDto openBankingAccountRequestDto){
        String url = BASE_URL + "/v2.0/account/list";

        HttpEntity httpEntity = generateHttpEntity(generateHeader("Authorization", openBankingAccountRequestDto.getAccessToken()));

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("user_seq_no", openBankingAccountRequestDto.getUser_seq_no())
                .queryParam("include_cancel_yn", openBankingAccountRequestDto.getInclude_cancel_yn())
                .queryParam("sort_order", openBankingAccountRequestDto.getSort_order())
                .build();

        OpenBankingAccountListResponseDto openBankingAccountListResponseDto = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, OpenBankingAccountListResponseDto.class).getBody();

        if(!isCodeValid(openBankingAccountListResponseDto.getRsp_code())){
            log.error("error code : {}, error msg : {}", openBankingAccountListResponseDto.getRsp_code(), openBankingAccountListResponseDto.getRsp_message());
            throw new RuntimeException(openBankingAccountListResponseDto.getRsp_message());
        }

        return openBankingAccountListResponseDto;

    }

    /**
     * 잔액조회
     */
    public OpenBankingBalanceResponseDto requestBalance(OpenBankingBalanceRequestDto openBankingBalanceRequestDto){
        String url = BASE_URL + "/v2.0/account/balance/fin_num";

        HttpEntity httpEntity = generateHttpEntity(generateHeader("Authorization",openBankingBalanceRequestDto.getAccessToken()));

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("bank_tran_id", openBankingBalanceRequestDto.getBank_tran_id())
                .queryParam("fintech_use_num", openBankingBalanceRequestDto.getFintech_use_num())
                .queryParam("tran_dtime", openBankingBalanceRequestDto.getTran_dtime())
                .build();

        OpenBankingBalanceResponseDto openBankingBalanceResponseDto = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, OpenBankingBalanceResponseDto.class).getBody();

        if(!isCodeValid(openBankingBalanceResponseDto.getRsp_code())){
            log.error("error code : {}, error msg : {}", openBankingBalanceResponseDto.getRsp_code(), openBankingBalanceResponseDto.getRsp_message());
            throw new RuntimeException(openBankingBalanceResponseDto.getRsp_message());
        }

        return openBankingBalanceResponseDto;
    }

    /**
     * 거래내역조회
     *
     */
    public TransactionListResponseDto requestTransactionList(Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto, TransactionListRequestDto transactionListRequestDto){
        String url = BASE_URL + "/v2.0/account/transaction_list/fin_num";

        HttpEntity httpEntity = generateHttpEntity(generateHeader("Authorization", openbanking3LeggedTokenFromHeaderDto.getAccessToken()));
        transactionListRequestDto.setTran_dtime(OpenBankingUtil.transTime());

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("bank_tran_id", transactionListRequestDto.getBank_tran_id())
                .queryParam("fintech_use_num", transactionListRequestDto.getFintech_use_num())
                .queryParam("inquiry_type", transactionListRequestDto.getInquiry_type())
                .queryParam("inquiry_base", transactionListRequestDto.getInquiry_base())
                .queryParam("from_date", transactionListRequestDto.getFrom_date())
                .queryParam("to_date", transactionListRequestDto.getTo_date())
                .queryParam("sort_order", transactionListRequestDto.getSort_order())
                .queryParam("tran_dtime", transactionListRequestDto.getTran_dtime())
                .build();

        TransactionListResponseDto transactionListResponseDto = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, TransactionListResponseDto.class).getBody();

        return transactionListResponseDto;


    }


    /**
     * 출금이체(핀테크이용번호 사용)
     */
    public WithdrawResponseDto requestWithdraw(String access_token, WithdrawRequestDto withdrawRequestDto){
        String url = BASE_URL + "/v2.0/transfer/withdraw/fin_num";

        ResponseEntity<WithdrawRequestDto> body = new ResponseEntity<>(withdrawRequestDto, generateHeader("Authorization", access_token), HttpStatus.OK);

        WithdrawResponseDto withdrawResponseDto = restTemplate.exchange(url, HttpMethod.POST, body, WithdrawResponseDto.class).getBody();

        return withdrawResponseDto;

    }


    /**
     * 입금이체(핀테크이용번호 사용)
     */
    public DepositResponseDto requestDeposit(String access_token, DepositRequestDto depositRequestDto) {
        String url = BASE_URL + "/v2.0/transfer/deposit/fin_num";

        ResponseEntity<DepositRequestDto> body = new ResponseEntity<>(depositRequestDto, generateHeader("Authorization", access_token), HttpStatus.OK);

        DepositResponseDto depositResponseDto = restTemplate.exchange(url, HttpMethod.POST, body, DepositResponseDto.class).getBody();

        return depositResponseDto;


    }




    /**
     * Header 생성
     */
    private HttpHeaders generateHeader(String name, String val){
        HttpHeaders httpHeaders = new HttpHeaders();
        if(name.equals("Authorization")){
            httpHeaders.add(name, "Bearer " + val);
            return httpHeaders;
        }

        httpHeaders.add(name, val);
        return httpHeaders;
    }

    /**
     * HttpEntity 생성(header만)
     */
    private HttpEntity generateHttpEntity(HttpHeaders httpHeaders){
        return new HttpEntity<>(httpHeaders);
    }

    /**
     * HttpEntity 생성(header + body)
     */
    private HttpEntity generateHttpEntityWithBody(HttpHeaders httpHeaders, MultiValueMap body) {
        return new HttpEntity<>(body, httpHeaders);
    }

    private boolean isCodeValid(String code){
        if(code == null || code.equals(SUCCESS_CODE) || code.equals("A0002")) return true; // A0002는 거래내역이 없어서 발생하는 것

        return false;
    }



}
