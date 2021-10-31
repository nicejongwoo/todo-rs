package com.example.todo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TokenProviderTest {

    final byte[] SECRET_KEY = DatatypeConverter.parseBase64Binary("secretKey");

    @DisplayName("jjwt 라이브러리 토큰 테스트")
    @Test
    void jjwtTest(){
        String okta_token = Jwts.builder().addClaims(
                        Map.of("name", "jongwoo", "age", 33)
                ).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setSubject("email@email.com")
                .setIssuer("company")
                .setIssuedAt(new Date())
                .compact();
        System.out.println(okta_token);
        printTokenInfo(okta_token);

        Jws<Claims> tokenInfo = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(okta_token);
        System.out.println(tokenInfo);
    }


    @DisplayName("java-jwt 라이브러리 토큰 테스트")
    @Test
    void javaJwtTest(){
        String oauth0_token = JWT.create().withClaim("name", "jongwoo")
                .withClaim("age", 33)
                .sign(Algorithm.HMAC256(SECRET_KEY));
        System.out.println(oauth0_token);
        printTokenInfo(oauth0_token);

        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(oauth0_token);
        System.out.println(verify.getClaims());
    }


    @DisplayName("만료시간 테스트")
    @Test
    void expiredTest() throws InterruptedException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        String token = JWT.create().withSubject("test")
                .withNotBefore(new Date(System.currentTimeMillis() + 1000))
                .withExpiresAt(new Date(System.currentTimeMillis() + 3000))
                .sign(algorithm);

//        Thread.sleep(2000);
        try {
            DecodedJWT verify = JWT.require(algorithm).build().verify(token);
            System.out.println(verify.getClaims());
        } catch (Exception e) {
            System.out.println("유효하지 않은 토큰입니다...");
            DecodedJWT decode = JWT.decode(token);
            System.out.println(decode.getClaims());
        }

    }

    private void printTokenInfo(String token) {
        String[] tokens = token.split("\\.");
        System.out.println("header: " + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("body: " + new String(Base64.getDecoder().decode(tokens[1])));
    }
}