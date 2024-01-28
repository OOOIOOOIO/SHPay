package com.sh.shpay.domain.openbanking.api;

import com.sh.shpay.domain.openbanking.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.api.dto.res.OpenBankingUserTokenResponseDto;
import com.sh.shpay.domain.openbanking.application.OpenBankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openbanking")
public class OpenBankingController {

    private final OpenBankingService openBankingService;


    /**
     * 사용자 토큰 발급 요청, 3-legged
     * auth-result-page
     *
     * code 받음
     * 3000113 : code가 만료 되었을 때
     * 3000114 : 콜백url이 다를떄
     *
     */
    @GetMapping("/token/request")
    public ResponseEntity<OpenBankingUserTokenResponseDto> requestUserToken(@RequestParam(name = "code") String code,
                                         @RequestParam(name = "scope") String scope,
                                         @RequestParam(name = "state") String state) {
        log.info("==== OpenBankingController | api/openbanking/token/request === ");

        /**
         * userId 수정
         */
        OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto = new OpenBankingUserCodeRequestDto(code, 1L);

        OpenBankingUserTokenResponseDto openBankingUserTokenResponseDto = openBankingService.requestUserToken(openBankingUserCodeRequestDto);

        log.info("access_token : " + openBankingUserTokenResponseDto.getAccess_token());
        log.info("access_token : " + openBankingUserTokenResponseDto.getUser_seq_no());
        log.info("access_token : " + openBankingUserTokenResponseDto.getRefresh_token());
        log.info("access_token : " + openBankingUserTokenResponseDto.getExpires_in());
        log.info("access_token : " + openBankingUserTokenResponseDto.getToken_type());
        log.info("access_token : " + openBankingUserTokenResponseDto.getScope());


        return new ResponseEntity<>(openBankingUserTokenResponseDto, HttpStatus.OK);
    }

    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     *
     * refreshToken Header에서 파싱하는 거로 수정
     */
    @GetMapping("/token/refresh")
    public ResponseEntity<OpenBankingUserRefreshTokenResponseDto> refreshUserToken(@RequestParam(name = "refresh_token") String refreshToken){

        OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto = null;

        return new ResponseEntity<>(openBankingUserRefreshTokenResponseDto, HttpStatus.OK);
    }


}
