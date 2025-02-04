package com.busanit501.api5012.security.filter;

import com.busanit501.api5012.util.JWTUtil;
import com.google.gson.Gson;
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
                                    FilterChain filterChain) throws ServletException,  IOException {

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
        try {
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

            // 이후 로직 추가: Refresh Token 검증 및 처리

        } catch (Exception e) {
            log.error("Error parsing request JSON: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid JSON format.");
            return;
        }


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
}