package com.sh.shpay.global.session.interceptor.sessionCheckInterceptor;

import com.sh.shpay.global.common.SessionConst;
import com.sh.shpay.global.session.resolver.usersession.UserInfoFromSessionDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
public class UserSessionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            HttpSession session = request.getSession(false);
            UserInfoFromSessionDto userInfo = (UserInfoFromSessionDto)session.getAttribute(SessionConst.COMMON_USER.getRule());

        } catch (Exception e) {
            throw new RuntimeException("interceptor : 유저 없음");
        }

        return true;

    }

}
