package io.makeat.makeat_be.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import io.makeat.makeat_be.utils.JwtUtil;

@Slf4j
@Service
public class LoginService {

    @Value("${jwt.secret}")
    private String secret;

    private Long expireMs = 1000 * 60 * 60 * 24L; // 24시간

    public String login(String loginKind, String loginId) {
        return JwtUtil.createJwt(loginKind, loginId, secret, expireMs);
    }

}
