package com.sh.shpay.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.shpay.global.exception.CustomException;
import com.sh.shpay.global.exception.ErrorCode;
import com.sh.shpay.global.exception.custom.JwtTokenExpiredException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            throw new CustomException(ErrorCode.SignatureException);
        } catch (MalformedJwtException e) {
            throw new CustomException(ErrorCode.MalformedJwtException);
        } catch (JwtTokenExpiredException e) {
            throw new CustomException(ErrorCode.JwtTokenExpiredException);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.UnsupportedJwtException);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.IllegalArgumentException);
        } catch (UsernameNotFoundException e) {
            throw new CustomException(ErrorCode.UsernameNotFoundException);
        }
    }



}
