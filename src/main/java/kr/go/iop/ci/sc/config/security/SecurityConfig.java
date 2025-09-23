package kr.go.iop.ci.sc.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationApiFilter jwtAuthenticationApiFilter;

    /**
     * JWT 인증 필터가 적용된 SecurityFilterChain
     */
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProvider);

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/userList", "/auth/**", "/", "/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationApiFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}
