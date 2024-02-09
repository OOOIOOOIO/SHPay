package com.sh.shpay.global.resolver.session;

import com.sh.shpay.global.common.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@Slf4j
public class UserInfoFromSessionResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(UserInfoFromSession.class) != null &&
                parameter.getParameterType().equals(UserInfoFromSessionDto.class);
    }

    @Override
    public UserInfoFromSessionDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        UserInfoFromSessionDto userInfoFromSessionDto = (UserInfoFromSessionDto) session.getAttribute(SessionConst.COMMON_USER.name());


        log.info("========== Get UserInfo From Session by ArgumentResolver ==========");
        log.info("========== Get email : " + userInfoFromSessionDto.getEmail() + " ==========");
        log.info("========== Get userID : " + userInfoFromSessionDto.getUserId() + " ==========");

        return userInfoFromSessionDto;

    }
}
