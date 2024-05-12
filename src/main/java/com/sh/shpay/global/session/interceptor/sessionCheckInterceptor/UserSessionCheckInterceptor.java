package com.sh.shpay.global.session.interceptor.sessionCheckInterceptor;

import com.sh.shpay.global.common.SessionConst;
import com.sh.shpay.global.resolver.session.UserInfoFromSessionDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
public class UserSessionCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            HttpSession session = request.getSession(false);
            UserInfoFromSessionDto userInfo = (UserInfoFromSessionDto)session.getAttribute(SessionConst.COMMON_USER.name());

            log.info(" ====== UserSessionCheck Interceptor ======");
            log.info("userId : " + userInfo.getUserId());
            log.info("email : " + userInfo.getEmail());

        } catch (Exception e) {
            throw new RuntimeException("interceptor : 유저 없음");
        }

        return true;

    }

}
