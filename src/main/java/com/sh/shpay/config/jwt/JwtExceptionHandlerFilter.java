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
            errorResponse(request, response, e, new CustomException(ErrorCode.SignatureException));
        } catch (MalformedJwtException e) {
            errorResponse(request, response, e, new CustomException(ErrorCode.MalformedJwtException));
        } catch (JwtTokenExpiredException e) {
            errorResponse(request, response, e, new CustomException(ErrorCode.JwtTokenExpiredException));
        } catch (UnsupportedJwtException e) {
            errorResponse(request, response, e, new CustomException(ErrorCode.UnsupportedJwtException));
        } catch (IllegalArgumentException e) {
            errorResponse(request, response, e, new CustomException(ErrorCode.IllegalArgumentException));
        } catch (UsernameNotFoundException e) {
            errorResponse(request, response, e, new CustomException(ErrorCode.UsernameNotFoundException));
        }
    }

    private static void errorResponse(HttpServletRequest request, HttpServletResponse response, Exception e, CustomException customException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 error

        final Map<String, Object> body = new HashMap<>();
        body.put("errorCode ", customException.getErrorCode().getCode());
        body.put("date", new Date());
        body.put("message", customException.getErrorCode().getMessage());
        body.put("request", request.getRequestURI());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }


}
