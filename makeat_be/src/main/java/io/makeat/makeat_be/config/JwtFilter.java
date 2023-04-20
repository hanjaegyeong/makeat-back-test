package io.makeat.makeat_be.config;

import io.makeat.makeat_be.entity.User;
import io.makeat.makeat_be.repository.UserRepository;
import io.makeat.makeat_be.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader("Authorization");
        log.info("authorization: {}", authorization);

        // token 안보내면 block
        if (authorization == null) {
            log.error("authorization 이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        //Token 꺼내기
        String jwt = authorization.split(" ")[1];

        // 토큰이 Expired 되었는지
        if(JwtUtil.isExpired(jwt, secret)) {
            log.error("토큰이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // 받은 토큰을 해석해 유저 정보를 가져온다
        String loginKind = JwtUtil.getLoginKind(jwt, secret);
        String loginId = JwtUtil.getLoginId(jwt, secret);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginKind, loginId);

        // 디테일
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
