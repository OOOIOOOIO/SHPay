package com.sh.shpay.global.resolver.token.two;

import com.sh.shpay.domain.openbanking.token.domain.three.OpenBanking3LeggedToken;
import com.sh.shpay.domain.openbanking.token.domain.three.repository.OpenBanking3LeggedTokenRepository;
import com.sh.shpay.domain.openbanking.token.domain.two.OpenBanking2LeggedToken;
import com.sh.shpay.domain.openbanking.token.domain.two.repository.OpenBanking2LeggedTokenRepository;
import com.sh.shpay.global.exception.CustomException;
import com.sh.shpay.global.exception.ErrorCode;
import com.sh.shpay.global.resolver.token.three.Openbanking3LeggedTokenFromHeaderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Component
//public class TokenInfoFromHeaderArgumentResolver implements HandlerMethodArgumentResolver {
public class Openbanking2LeggedTokenFromHeaderResolver implements HandlerMethodArgumentResolver {


    private final OpenBanking2LeggedTokenRepository openBanking2LeggedTokenRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Openbanking3LeggedTokenFromHeaderDto.class);
    }

    /**
     * Header에서 accessToken, refreshToken 추출
     */
    @Override
    public Openbanking2LeggedTokenFromHeaderDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(); // jakarta로 변경 후 getNativeRequest 붙여줘야 함

        String accessToken = request.getHeader("Authorization").substring(7);
        String refreshToken = request.getHeader("refresh_token");

//        if(checkExpireTime(accessToken)){
//            throw new CustomException(ErrorCode.ExpireAccessTokenException);
//        }


        Openbanking2LeggedTokenFromHeaderDto openbanking2LeggedTokenFromHeaderDto = Openbanking2LeggedTokenFromHeaderDto.builder()
                .accessToken(accessToken)
                .build();


        log.info("========== Openbanking Token Info From Header by ArgumentResolver ==========");
        log.info("========== accessToken : " + openbanking2LeggedTokenFromHeaderDto.getAccessToken() + " ==========");

        return openbanking2LeggedTokenFromHeaderDto;
    }

    private boolean checkExpireTime(String accessToken){
        OpenBanking2LeggedToken openBanking2LeggedToken = openBanking2LeggedTokenRepository.findByAccessToken(accessToken).orElseThrow(() -> new CustomException(ErrorCode.NotExistAccessTokenException));

        LocalDateTime createdAt = openBanking2LeggedToken.getCreatedAt();
        Long expireMin = openBanking2LeggedToken.getExpireMin();
        long expireDay = expireMin / 216000;
        LocalDateTime expireDateTime = createdAt.plusDays(expireDay);

        log.info(""+expireDateTime);
        log.info(""+LocalDateTime.now());

        if(LocalDateTime.now().isBefore(expireDateTime)){
            return false;
        }

        return true;
    }
}
