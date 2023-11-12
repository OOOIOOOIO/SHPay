package com.sh.shpay.common;

import com.sh.shpay.domain.openbanking.api.dto.req.*;
import com.sh.shpay.domain.openbanking.api.dto.res.*;
import com.sh.shpay.util.openbanking.OpenBankingUtil;
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
    private static final String SUCCESS_CODE = "AOO0O"; // 나중에 수정?


    /**
     * 토큰 발급 요청
     */
    public OpenBankingUserResponseTokenDto requestUserToken(OpenBankingUserRequestTokenDto openBankingUserRequestToken){
        HttpHeaders httpHeaders = generateHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        HttpEntity httpEntity = generateHttpEntityWithBody(httpHeaders, openBankingUserRequestToken.toMultiValueMap());

        OpenBankingUserResponseTokenDto openBankingUserResponseToken = restTemplate.exchange(BASE_URL + "/oauth/2.0/token", HttpMethod.POST, httpEntity, OpenBankingUserResponseTokenDto.class).getBody();

        if(!isCodeValid(openBankingUserResponseToken.getRsp_code())){
            log.error("error code : {}, error msg : {}", openBankingUserResponseToken.getRsp_code(), openBankingUserResponseToken.getRsp_message());
            throw new RuntimeException(openBankingUserResponseToken.getRsp_message());
        }

        return openBankingUserResponseToken;

    }

    /**
     * 계좌조회
     */
    public OpenBankingSearchAccountResponseDto requestAccountList(OpenBankingSearchAccountRequestDto openBankingAccountRequestDto){
        String url = BASE_URL + "/v2.0/account/list";

        HttpEntity httpEntity = generateHttpEntity(generateHeader("Authorization", openBankingAccountRequestDto.getAccessToken()));

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("user_seq_no", openBankingAccountRequestDto.getUser_seq_no())
                .queryParam("include_cancel_yn", openBankingAccountRequestDto.getInclude_cancel_yn())
                .queryParam("sort_order", openBankingAccountRequestDto.getSort_order())
                .build();

        OpenBankingSearchAccountResponseDto openBankingSearchAccountResponseDto = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, OpenBankingSearchAccountResponseDto.class).getBody();

        if(!isCodeValid(openBankingSearchAccountResponseDto.getRsp_code())){
            log.error("error code : {}, error msg : {}", openBankingSearchAccountResponseDto.getRsp_code(), openBankingSearchAccountResponseDto.getRsp_message());
            throw new RuntimeException(openBankingSearchAccountResponseDto.getRsp_message());
        }

        return openBankingSearchAccountResponseDto;

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
     * 계좌이체
     */
    public OpenBankingTransferResponseDto requestTransfer(String access_token, OpenBankingTransferRequestDto openBankingTransferRequestDto){
        String url = BASE_URL + "/v2.0/transfer/withdraw/fin_num";

        openBankingTransferRequestDto.setTran_dtime(OpenBankingUtil.transTime());

        ResponseEntity<OpenBankingTransferRequestDto> param = new ResponseEntity<>(openBankingTransferRequestDto, generateHeader("Authorization", access_token), HttpStatus.OK);

        OpenBankingTransferResponseDto transferResponseDto = restTemplate.exchange(url, HttpMethod.POST, param, OpenBankingTransferResponseDto.class).getBody();

        return transferResponseDto;

    }


    /**
     * 사용자 정보 가져오기 - ci값
     */
    public OpenBankingUserInfoResponseDto requestUserInfo(OpenBankingUserInfoRequestDto openBankingUserInfoRequestDto) {
        String url = BASE_URL + "/v2.0/user/me";

        HttpEntity httpEntity = generateHttpEntity(generateHeader("Authorization", openBankingUserInfoRequestDto.getAccessToken()));

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("user_seq_no", openBankingUserInfoRequestDto.getUser_seq_no())
                .build();

        OpenBankingUserInfoResponseDto openBankingUserInfoResponseDto = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, OpenBankingUserInfoResponseDto.class).getBody();

        if(!isCodeValid(openBankingUserInfoResponseDto.getRsp_code())){
            log.error("error code : {}, error msg : {}", openBankingUserInfoResponseDto.getRsp_code(), openBankingUserInfoResponseDto.getRsp_message());
            throw new RuntimeException(openBankingUserInfoResponseDto.getRsp_message());
        }

        return openBankingUserInfoResponseDto;

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
        if(code == null || code.equals(SUCCESS_CODE)) return true;

        return false;
    }



}
