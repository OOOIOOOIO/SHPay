package com.sh.shpay.domain.openbanking.token.api;

import com.sh.shpay.domain.openbanking.openbanking.dto.res.OpenBankingUserInfoResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.req.OpenBankingUserCodeRequestDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser2leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUserRefreshTokenResponseDto;
import com.sh.shpay.domain.openbanking.token.api.dto.res.OpenBankingUser3leggedTokenResponseDto;
import com.sh.shpay.domain.openbanking.openbanking.application.OpenBankingService;
import com.sh.shpay.domain.openbanking.token.application.OpenBankingTokenService;
import com.sh.shpay.global.log.LogTrace;
import com.sh.shpay.global.resolver.session.UserInfoFromSession;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeader;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeaderDto;
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
    @GetMapping("/token3/request")
    public ResponseEntity<OpenBankingUser3leggedTokenResponseDto> requestUser3LeggedToken(@RequestParam(name = "code") String code,
                                                                                   @RequestParam(name = "scope") String scope,
                                                                                   @RequestParam(name = "state") String state,
                                                                                   @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {

        OpenBankingUserCodeRequestDto openBankingUserCodeRequestDto = new OpenBankingUserCodeRequestDto(code, userInfoFromSessionDto.getUserId());

        OpenBankingUser3leggedTokenResponseDto openBankingUser3leggedTokenResponseDto = openBankingService.requestUser3leggedToken(openBankingUserCodeRequestDto, state);

        // 여기서 token 정보 저장
        openBankingTokenService.saveOpenBanking3LeggedToken(openBankingUser3leggedTokenResponseDto, userInfoFromSessionDto.getUserId());

        return new ResponseEntity<>(openBankingUser3leggedTokenResponseDto, OK);
    }

    /**
     * 사용자 토큰 갱신(Access Token), 3-legged
     *
     */
    @Operation(
            summary = "3-legged Token 갱신 API",
            description = "Openbanking Token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Token 갱신에 성공하였습니다."
    )
    @LogTrace
    @PostMapping("/token3/refresh")
    public ResponseEntity<OpenBankingUserRefreshTokenResponseDto> refreshUser3LeggedToken(@Openbanking3LeggedTokenFromHeader Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto,
                                                                                   @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto){

        OpenBankingUserRefreshTokenResponseDto openBankingUserRefreshTokenResponseDto = openBankingService.refreshUserToken(openbanking3LeggedTokenFromHeaderDto);

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

        openBankingTokenService.saveOpenBanking2LeggedToken(openBankingUser2leggedTokenResponseDto, userInfoFromSessionDto.getUserId());

        return new ResponseEntity<>(openBankingUser2leggedTokenResponseDto, OK);
    }

    /**
     * 사용자 토큰 갱신 요청, 2-legged
     *
     */
    @Operation(
            summary = "2-legged Token 갱신 API",
            description = "Openbanking Token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Token 갱신에 성공하였습니다."
    )
    @LogTrace
    @PostMapping("/token2/refresh")
    public ResponseEntity<OpenBankingUser2leggedTokenResponseDto> refreshUser2LeggedToken(@UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {

        openBankingTokenService.deleteOpenBankingUser2leggedToken(userInfoFromSessionDto.getUserId());


        OpenBankingUser2leggedTokenResponseDto openBankingUser2leggedTokenResponseDto = openBankingService.requestUser2leggedToken();

        openBankingTokenService.saveOpenBanking2LeggedToken(openBankingUser2leggedTokenResponseDto, userInfoFromSessionDto.getUserId());

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
    public ResponseEntity<OpenBankingUserInfoResponseDto> requestUserInfo(@Openbanking3LeggedTokenFromHeader Openbanking3LeggedTokenFromHeaderDto openbanking3LeggedTokenFromHeaderDto,
                                                                          @UserInfoFromSession UserInfoFromSessionDto userInfoFromSessionDto) {

        OpenBankingUserInfoResponseDto openBankingUserInfoResponseDto = openBankingService.requestUserInfo(openbanking3LeggedTokenFromHeaderDto, userInfoFromSessionDto);


        return new ResponseEntity<>(openBankingUserInfoResponseDto, OK);
    }

}
