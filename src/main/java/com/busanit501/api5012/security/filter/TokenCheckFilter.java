package com.busanit501.api5012.security.filter;

import com.busanit501.api5012.security.exception.AccessTokenException;
import com.busanit501.api5012.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 요청 경로 가져오기
        String path = request.getRequestURI();

        // 회원 가입시, /api/member/join, 토큰 검사한다.
        // 회원 가입시, /member/join, 토큰 검사 안함
        // "/api/"로 시작하지 않는 경로는 필터 처리하지 않음
        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // /api , 정보 요청.
        // 로그 출력
        log.info("Token Check Filter triggered...");
        log.info("JWTUtil instance: {}", jwtUtil);

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    //토큰 검사하는 도구.
    public Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
        String headerStr = request.getHeader("Authorization");

        // 1. Authorization 헤더가 없는 경우
        if (headerStr == null || headerStr.length() < 8) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        // 2. 토큰 타입 확인
        String tokenType = headerStr.substring(0, 6);
        String tokenStr = headerStr.substring(7);

        if (!tokenType.equalsIgnoreCase("Bearer")) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try {
            // 3. JWT 검증
            Map<String, Object> values = jwtUtil.validateToken(tokenStr);
            return values;

        } catch (MalformedJwtException malformedJwtException) {
            log.error("MalformedJwtException: Invalid token format.");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);

        } catch (SignatureException signatureException) {
            log.error("SignatureException: Invalid token signature.");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);

        } catch (ExpiredJwtException expiredJwtException) {
            log.error("ExpiredJwtException: Token has expired.");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }
}