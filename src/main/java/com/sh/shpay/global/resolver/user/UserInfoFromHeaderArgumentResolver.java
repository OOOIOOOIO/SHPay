package com.sh.shpay.global.resolver.user;

import com.sh.shpay.global.common.SessionConst;
import com.sh.shpay.global.session.resolver.usersession.UserInfoFromSessionDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
public class UserInfoFromHeaderArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserInfoFromHeaderDto.class);
    }

    /**
     * session에서 추출
     */
    @Override
    public UserInfoFromSessionDto resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest;
        HttpSession session = request.getSession();

        UserInfoFromSessionDto userInfoFromSessionDto = (UserInfoFromSessionDto) session.getAttribute(SessionConst.COMMON_USER.name());



        log.info("========== UserInfo From Session by ArgumentResolver ==========");
        log.info("========== Get email : " + userInfoFromSessionDto.getEmail() + " ==========");

        return userInfoFromSessionDto;
    }
}
