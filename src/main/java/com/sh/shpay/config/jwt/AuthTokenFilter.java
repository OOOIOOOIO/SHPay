package com.sh.shpay.config.jwt;

import com.sh.shpay.util.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * doFilterInternal() 내부에서 수행하는 작업:
 * – HTTP 쿠키에서 JWT 가져오기
 * – 요청에 JWT가 있으면 유효성을 검사하고 사용자 이름을 구문 분석합니다.
 * – 사용자 이름에서 UserDetails를 가져와 인증 개체를 만듭니다.
 * – setAuthentication(authentication) 메서드를 사용하여 SecurityContext에서 현재 UserDetails를 설정합니다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



            // request : 헤더에서 넘어오는 JWT
            String jwt = parseJwt(request);

            // jwt 토큰 검사
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

//                String username = jwtUtils.generateTokenFromUsername(jwt);
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                null,
                        userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }


        filterChain.doFilter(request, response);
    }

    /**
     * request header에서 jwt(xxx.xxx.xxx) 조회
     *
     * header 이름 : jwt-auth-token
     */
    private String parseJwt(HttpServletRequest request) {

        // header에서 xxx.xxx.xxx 가져오기
        String jwt = request.getHeader("jwt-auth-token");

        return jwt;
    }
}
