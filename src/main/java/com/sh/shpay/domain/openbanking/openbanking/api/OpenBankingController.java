package com.sh.shpay.domain.openbanking.openbanking.api;

import com.sh.shpay.domain.openbanking.openbanking.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingUserInfoResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingUserTokenResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.token.application.OpenBankingTokenService;
import com.sh.shpay.domain.users.application.UsersService;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeader;
import com.sh.shpay.global.resolver.token.TokenInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openbanking")
public class OpenBankingController {

    private final OpenBankingService openBankingService;
    private final OpenBankingTokenService openBankingTokenService;



    /**
     * 사용자 토큰 발급 요청, 3-legged
     *
     * response param : code, scope, client_info, state
     *
     * 3000113 : code가 만료 되었을 때
     * 3000114 : 콜백url이 다를떄
     *
     * state 비고해야함
     */
    @GetMapping("/token/request")
    public ResponseEntity<OpenBankingUserTokenResponseDto> requestUserToken(@RequestParam(name = "code") String code,
                                                                            @RequestParam(name = "scope") String scope,
                                                                            @RequestParam(name = "state") String state,
                                                                            @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {
        log.info("================= OpenBankingController | api/openbanking/token/request =================");
        log.info("code : " + code);
        log.info("scope : " + scope);

        OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto = new OpenBankingUserCodeRequestDto(code, userInfoFromSessionDto.getUserId());

        OpenBankingUserTokenResponseDto openBankingUserTokenResponseDto = openBankingService.requestUserToken(openBankingUserCodeRequestDto);

        /**
         * 여기서 token 정보 저장
         */
        openBankingTokenService.saveOpenBankingUserToken(openBankingUserTokenResponseDto, userInfoFromSessionDto.getUserId());

        log.info("access_token : " + openBankingUserTokenResponseDto.getAccess_token());
        log.info("user_seq_no : " + openBankingUserTokenResponseDto.getUser_seq_no());
        log.info("refresh_token : " + openBankingUserTokenResponseDto.getRefresh_token());
        log.info("expires_in : " + openBankingUserTokenResponseDto.getExpires_in());
        log.info("token_type : " + openBankingUserTokenResponseDto.getToken_type());
        log.info("scope : " + openBankingUserTokenResponseDto.getScope());


        return new ResponseEntity<>(openBankingUserTokenResponseDto, OK);
    }

    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     *
     * refreshToken Header에서 파싱하는 거로 수정
     *
     * GET/POST 둘 다 가능
     */
    @PostMapping("/token/refresh")
    public ResponseEntity<OpenBankingUserRefreshTokenResponseDto> refreshUserToken(@TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto,
                                                                                   @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){

        log.info("================= OpenBankingController | api/openbanking/token/request =================");

        OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto = openBankingService.refreshUserToken(tokenInfoFromHeaderDto);

        // token 저장
        openBankingTokenService.updateTokenInfo(openBankingUserRefreshTokenResponseDto, userInfoFromSessionDto.getUserId());

        log.info("access_token : " + openBankingUserRefreshTokenResponseDto.getAccess_token());
        log.info("user_seq_no : " + openBankingUserRefreshTokenResponseDto.getUser_seq_no());
        log.info("refresh_token : " + openBankingUserRefreshTokenResponseDto.getRefresh_token());
        log.info("expires_in : " + openBankingUserRefreshTokenResponseDto.getExpires_in());
        log.info("token_type : " + openBankingUserRefreshTokenResponseDto.getToken_type());
        log.info("scope : " + openBankingUserRefreshTokenResponseDto.getScope());

        return new ResponseEntity<>(openBankingUserRefreshTokenResponseDto, OK);
    }

    /**
     * 사용자 정보 가져오기 - ci값, 계좌 리스트 등등
     *
     * ci값 저장
     * 보류 -> 계좌 등록 후 바로 부르게 하기, DB
     */
    @GetMapping("/user/me")
    public ResponseEntity<OpenBankingUserInfoResponseDto> requestUserInfo(@TokenInfoFromHeader TokenInfoFromHeaderDto tokenInfoFromHeaderDto,
                                                          @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {

        OpenBankingUserInfoResponseDto openBankingUserInfoResponseDto = openBankingService.requestUserInfo(tokenInfoFromHeaderDto, userInfoFromSessionDto);


        return new ResponseEntity<>(openBankingUserInfoResponseDto, OK);
    }

}
