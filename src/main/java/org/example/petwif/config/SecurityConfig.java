package org.example.petwif.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())   // CSRF 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())  // HTTP Basic 비활성화
                .formLogin(formLogin -> formLogin.disable())  // 폼 로그인 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll()  // 이 경로들은 인증 없이 접근 허용
                        .anyRequest().authenticated()  // 그 외의 모든 요청은 인증 필
                );

        return http.build();
    }
// 애초에 막혀있어서 못했네...

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}

