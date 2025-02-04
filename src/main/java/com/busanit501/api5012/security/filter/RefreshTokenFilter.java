package com.busanit501.api5012.security.filter;

import com.busanit501.api5012.security.exception.RefreshTokenException;
import com.busanit501.api5012.util.JWTUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshPath;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,  IOException
    {

        String path = request.getRequestURI();

        // 1. 요청 경로가 refreshPath와 일치하지 않으면 필터 통과
        if (!path.equals(refreshPath)) {
            log.info("Skipping refresh token filter...");
            filterChain.doFilter(request, response);
            return;
        }

        // 2. refreshPath 요청에 대한 처리 로직
        log.info("Refresh Token Filter triggered for path: {}", path);

// 2. 요청에서 accessToken과 refreshToken 추출

            Map<String, String> tokens = parseRequestJSON(request);
            if (tokens == null || !tokens.containsKey("accessToken") || !tokens.containsKey("refreshToken")) {
                log.error("Missing tokens in request.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing accessToken or refreshToken.");
                return;
            }

            String accessToken = tokens.get("accessToken");
            String refreshToken = tokens.get("refreshToken");

            log.info("accessToken: {}", accessToken);
            log.info("refreshToken: {}", refreshToken);

            // 이후 로직 추가: Access Token 검증 및 처리
            try {
                // Access Token 검증
                checkAccessToken(accessToken);
            } catch (RefreshTokenException refreshTokenException) {
                // RefreshTokenException 발생 시 응답 에러 전송 및 종료
                refreshTokenException.sendResponseError(response);
                return; // 더 이상 실행하지 않음
            }

            // 이후 로직 추가: Refresh Token 검증 및 처리




    }

    /**
     * 요청에서 JSON 데이터를 Map으로 변환
     */
    private Map<String, String> parseRequestJSON(HttpServletRequest request) {
        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            log.error("Error reading JSON from request: {}", e.getMessage());
        }
        return null;
    }

    // 액세스 토큰 검사 도구 추가.
    private void checkAccessToken(String accessToken) throws RefreshTokenException {
        try {
            // Access Token 검증
            jwtUtil.validateToken(accessToken);
        } catch (ExpiredJwtException expiredJwtException) {
            // Access Token 만료 시 로그 출력
            log.info("Access Token has expired.");
        } catch (Exception exception) {
            // 기타 검증 실패 시 예외 발생
            log.error("Access Token validation failed: {}", exception.getMessage());
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    // 리플레쉬 토큰 검사 도구 또 추가 될 예정.
}