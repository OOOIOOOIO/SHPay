package com.sh.shpay.domain.openbanking.api;

import com.sh.shpay.domain.openbanking.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.api.dto.res.OpenBankingUserTokenResponseDto;
import com.sh.shpay.domain.openbanking.application.OpenBankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openbanking")
public class OpenBankingController {

    private final OpenBankingService openBankingService;

    @GetMapping("/test")
    public void abb(){
        log.info("twtestsett");
    }
    /**
     * 토큰 발급(AuthToken)
     */
    @PostMapping("/token")
    public void aa() {


    }

    /**
     * 토큰 받는 페이지
     * auth-result-page
     *
     * code 받음
     * 3000113 : code가 만료 되었을 때
     * 3000114 : 콜백url이 다를떄
     *
     */
    @GetMapping("/authResult")
    public String authResultPage(@RequestParam(name = "code") String code,
                                 @RequestParam(name = "scope") String scope,
                                 @RequestParam(name = "state") String state) {

        log.info("code :" + code);
        log.info("scope :" + scope);
        log.info("state :" + state);

        log.info("==========");
        OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto = new OpenBankingUserCodeRequestDto(code, 1L);

        OpenBankingUserTokenResponseDto openBankingUserTokenResponseDto = openBankingService.requestUserToken(openBankingUserCodeRequestDto);

        log.info("==========");
        log.info(openBankingUserTokenResponseDto.getAccess_token());
        log.info(openBankingUserTokenResponseDto.getUser_seq_no());
        log.info(openBankingUserTokenResponseDto.getRefresh_token());

        return "auth-result-page";
    }


}
