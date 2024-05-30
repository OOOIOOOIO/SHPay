package com.sh.shpay.domain.openbanking.token.api;

import com.sh.shpay.domain.openbanking.openbanking.api.dto.res.OpenBankingUserInfoResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser2leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser3leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.token.application.OpenBankingTokenService;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.OpenbankingTokenInfoFromHeader;
import com.sh.shpay.global.resolver.token.OpenbankingTokenInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Openbanking - Token", description = "Openbanking Token API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openbanking")
public class OpenBankingTokenController {

    private final OpenBankingService openBankingService;
    private final OpenBankingTokenService openBankingTokenService;



    /**
     * code 요청 후
     * 사용자 토큰 발급 요청, 3-legged
     *
     * response param : code, scope, client_info, state
     *
     * 3000113 : code가 만료 되었을 때
     * 3000114 : 콜백url이 다를떄
     *
     * 주의1, state 비교
     */
    @Operation(
            summary = "3-legged Token 발급 API",
            description = "Openbanking Token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Token 발급에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("/token/request")
    public ResponseEntity<OpenBankingUser3leggedTokenResponseDto> requestUser3LeggedToken(@RequestParam(name = "code") String code,
                                                                                   @RequestParam(name = "scope") String scope,
                                                                                   @RequestParam(name = "state") String state,
                                                                                   @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {

        OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto = new OpenBankingUserCodeRequestDto(code, userInfoFromSessionDto.getUserId());

        OpenBankingUser3leggedTokenResponseDto openBankingUser3leggedTokenResponseDto = openBankingService.requestUser3leggedToken(openBankingUserCodeRequestDto, state);

        // 여기서 token 정보 저장
        openBankingTokenService.saveOpenBankingUserToken(openBankingUser3leggedTokenResponseDto, userInfoFromSessionDto.getUserId());

        return new ResponseEntity<>(openBankingUser3leggedTokenResponseDto, OK);
    }

    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     *
     * refreshToken Header에서 파싱하는 거로 수정
     *
     * GET/POST 둘 다 가능
     */
    @Operation(
            summary = "3-legged Token 갱신 API",
            description = "Openbanking Token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Token 갱신에 성공하였습니다."
    )
    @PostMapping("/token/refresh")
    public ResponseEntity<OpenBankingUserRefreshTokenResponseDto> refreshUser3LeggedToken(@OpenbankingTokenInfoFromHeader OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto,
                                                                                   @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){

        OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto = openBankingService.refreshUserToken(openbankingTokenInfoFromHeaderDto);

        // token 저장
        openBankingTokenService.updateTokenInfo(openBankingUserRefreshTokenResponseDto, userInfoFromSessionDto.getUserId());


        return new ResponseEntity<>(openBankingUserRefreshTokenResponseDto, OK);
    }

    /**
     * 사용자 토큰 발급 요청, 2-legged
     *
     */
    @Operation(
            summary = "2-legged Token 발급 API",
            description = "Openbanking Token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Token 발급에 성공하였습니다."
    )
    @LogTrace
    @PostMapping("/token/request")
    public ResponseEntity<OpenBankingUser2leggedTokenResponseDto> requestUser2LeggedToken(@UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {

        OpenBankingUser2leggedTokenResponseDto openBankingUser2leggedTokenResponseDto = openBankingService.requestUser2leggedToken();

        return new ResponseEntity<>(openBankingUser2leggedTokenResponseDto, OK);
    }

    /**
     * 사용자 정보 가져오기 - ci값, 계좌 리스트 등등
     *
     * ci값 저장
     */
    @Operation(
            summary = "user_ci 조회 발급 API",
            description = "Openbanking Token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "user_ci 조회에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("/user/me")
    public ResponseEntity<OpenBankingUserInfoResponseDto> requestUserInfo(@OpenbankingTokenInfoFromHeader OpenbankingTokenInfoFromHeaderDto openbankingTokenInfoFromHeaderDto,
                                                                          @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {

        OpenBankingUserInfoResponseDto openBankingUserInfoResponseDto = openBankingService.requestUserInfo(openbankingTokenInfoFromHeaderDto, userInfoFromSessionDto);


        return new ResponseEntity<>(openBankingUserInfoResponseDto, OK);
    }

}
