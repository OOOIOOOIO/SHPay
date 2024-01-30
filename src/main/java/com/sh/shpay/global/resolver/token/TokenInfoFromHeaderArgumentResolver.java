package com.sh.shpay.global.resolver.token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Slf4j
@RequiredArgsConstructor
@Component
//public class TokenInfoFromHeaderArgumentResolver implements HandlerMethodArgumentResolver {
public class TokenInfoFromHeaderArgumentResolver implements HandlerMethodArgumentResolver {




    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(TokenInfoFromHeaderDto.class);
    }

    /**
     * Header에서 accessToken, refreshToken 추출
     */
    @Override
    public TokenInfoFromHeaderDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(); // jakarta로 변경 후 getNativeRequest 붙여줘야 함

        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("refresh_token");


        TokenInfoFromHeaderDto tokenInfoFromHeaderDto = TokenInfoFromHeaderDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


        log.info("========== TokenInfo From Header by ArgumentResolver ==========");
        log.info("========== accessToken : " + tokenInfoFromHeaderDto.getAccessToken() + " ==========");
        log.info("========== refreshToken : " + tokenInfoFromHeaderDto.getRefreshToken() + " ==========");

        return tokenInfoFromHeaderDto;
    }
}
