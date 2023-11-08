package com.sh.shpay.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public OpenBankingUserRequestToken



}
