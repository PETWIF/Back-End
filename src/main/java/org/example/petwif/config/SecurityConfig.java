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
                        .requestMatchers("/signup/email", "/pet/add","/pet/edit",
                                "/blocks","/", "/member/**", "/verify").permitAll()  // 이 경로들은 인증 없이 접근 허용
                        .anyRequest().authenticated()  // 그 외의 모든 요청은 인증 필요

                        /*.logout().logoutSuccessUrl("/") //로그아웃 성공시 "/"주소로 이동
                        .and()
                        .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보 가져올 때의 설정 담당
                        .userService(customOAuth2UserService) //소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록 */
                );

        return http.build();
    }
// 애초에 막혀있어서 못했네...

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}

