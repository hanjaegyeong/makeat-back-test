package io.makeat.makeat_be.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String getLoginKind(String jwt, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt)
                .getBody().get("userKind", String.class);
    }
    public static String getLoginId(String jwt, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt)
                .getBody().get("userId", String.class);
    }
    public static boolean isExpired(String jwt, String secret) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt)
                    .getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public static String createJwt(String loginKind, String loginId, String secret, Long expireMs) {
        Claims claims = Jwts.claims();
        claims.put("userKind", loginKind);
        claims.put("userId", loginId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();
    }

}
