package com.busanit501.api5012.security.filter;

import com.busanit501.api5012.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
//
//        try {
//            // 3. Refresh Token 검증 (JWTUtil 사용)
//            String token = extractToken(request);
//            if (token != null) {
//                jwtUtil.validateToken(token); // 토큰 검증
//                log.info("Refresh Token is valid.");
//            } else {
//                log.warn("No Refresh Token found in the request.");
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("Missing Refresh Token");
//                return;
//            }
//
//        } catch (Exception e) {
//            log.error("Invalid Refresh Token: {}", e.getMessage());
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Invalid Refresh Token");
//            return;
//        }
//
//        // 4. 다음 필터로 전달
//        filterChain.doFilter(request, response);
//    }
//
//    /**
//     * Extracts the token from the Authorization header.
//     */
//    private String extractToken(HttpServletRequest request) {
//        String header = request.getHeader("Authorization");
//        if (header != null && header.startsWith("Bearer ")) {
//            return header.substring(7).trim();
//        }
//        return null;
    }
}