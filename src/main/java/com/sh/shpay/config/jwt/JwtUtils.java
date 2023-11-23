package com.sh.shpay.config.jwt;

import com.sh.shpay.global.exception.CustomException;
import com.sh.shpay.global.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

//    @Value("${jwt.secret}")
//    private String secret;
//    @Value("${jwt.expireMin}")
//    private Long expireMin;

    private final JwtInfoProperties jwtInfoProperties;

    /**
     * header에서 jwt-access 가져오기
     * header 이름 : jwt-auth-token
     */
    public String getJwtFromHeader(HttpServletRequest request) {

        return request.getHeader("jwt-auth-token");
    }

    /**
     * header에서 jwt-refresh 가져오기
     * header 이름 : jwt-auth-refresh-token
     */
    public String getJwtRefreshFromHeader(HttpServletRequest request) {
        return request.getHeader("jwt-auth-refresh-token");
    }



    /**
     * JWT에서 정보 가져오기(username)
     * subject : username(헤더이름)
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtInfoProperties.getSecret().getBytes()) // signature를 secrete key로 설정했는지, publickey로 설정했는지 확인! 나는 secret key로 설정
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     *  JWT 토큰 검사
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtInfoProperties.getSecret().getBytes()) // signature를 secrete key로 설정했는지, publickey로 설정했는지 확인! 나는 secret key로 설정
                    .build()
                    .parseClaimsJws(authToken);  // 여기서 Runtime Exception이 던져진다.

            return true;
        } catch (SignatureException e) {
            throw new CustomException(ErrorCode.SignatureException);
        } catch (MalformedJwtException e) {
            throw new CustomException(ErrorCode.MalformedJwtException);
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.JwtTokenExpiredException);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.UnsupportedJwtException);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.IllegalArgumentException);
        }

    }


    /**
     * JWT 토큰 생성
     * subject :username
     */
    public String generateTokenFromUsername(String username) {
        Key key = Keys.hmacShaKeyFor(jwtInfoProperties.getSecret().getBytes());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtInfoProperties.getExpireMin()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}