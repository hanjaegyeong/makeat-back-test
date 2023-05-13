package io.makeat.makeat_be.config;


import io.makeat.makeat_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()  // http basic 사용 안함
                .csrf().disable()   // csrf 사용 안함
                .cors().and()       // cors 사용 안함
                .authorizeHttpRequests(
                        auth ->auth
                                .requestMatchers("/**").permitAll()
//                                .requestMatchers("/user/naver").permitAll()  // 해당 url은 인증 안함
//                                .requestMatchers("/user/kakao").permitAll()
                                .anyRequest().authenticated()   // 나머지는 다 인증

                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt 토큰으로 인증하므로 세션 사용 안함
                .and().addFilterBefore(new JwtFilter(secret), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}