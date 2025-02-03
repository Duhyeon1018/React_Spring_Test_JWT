package com.busanit501.api5012.util;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Log4j2
@Component
public class JWTUtil {

    @Value("${com.busanit5012.jwt.secret}") // 비밀키를 외부 설정에서 가져옴
    private String key;


    public String generateToken(Map<String, Object> valueMap, int days) {
        log.info("Generating token with secret key: {}", key);
        return null;
    }


    public Map<String, Object> validateToken(String token) throws JwtException {
        log.info("Validating token: {}", token);

        Map<String, Object> claims = null;
        return claims;
    }
}