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
        /**
         * parseClaimsJws(): token을 BAse64로 디코딩 및 파싱
         * setSigningKey(): 헤더와 페이로드를 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
         * 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외
         * getSubject() -> (payload.sub)를 의미: 그중 우리는 userId가 필요하므로 getBody 호출
         */
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
