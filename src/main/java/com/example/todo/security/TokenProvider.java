package com.example.todo.security;

import com.example.todo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    private static final String SECRET_KEY = "password123";

    public String create(User user) {
        // 기한은 지금부터 1일
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) //header에 들어갈 내용 및 서명을 하기 위한 secret key
                .setSubject(user.getId()) // payload sub
                .setIssuer("todo") // payload iss
                .setIssuedAt(new Date()) // payload iat
                .setExpiration(expiryDate) // payload exp
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
