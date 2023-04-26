package io.makeat.makeat_be.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtUtil {

    private static byte[] getSecretKey(String secret) {
        return Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean isExpired(String jwt, String secret) {
        try {
            return Jwts.parser().setSigningKey(getSecretKey(secret)).parseClaimsJws(jwt)
                    .getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static Claims getClaims(String jwt, String secret) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSecretKey(secret)).build().parseClaimsJws(jwt)
                .getBody();
        log.info("claims: {}", claims);

        return claims;
    }

    public static String createJwt(String userPk, String secret, Long expireMs) {

        Claims claims = Jwts.claims().setSubject(userPk);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(SignatureAlgorithm.HS256, getSecretKey(secret))
                .compact();

        return accessToken;
    }
    // 1. refresh token 생성.
    // 2. 토큰을 들고 접근 시도.
}
