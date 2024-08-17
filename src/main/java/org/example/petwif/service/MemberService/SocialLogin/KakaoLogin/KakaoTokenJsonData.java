package org.example.petwif.service.MemberService.SocialLogin.KakaoLogin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class KakaoTokenJsonData {
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String TOKEN_URI;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String CLIENT_SECRET;



    public KakaoTokenResponse getToken(String code) {
        return webClient.post()
                .uri(TOKEN_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("grant_type", GRANT_TYPE)
                        .with("client_id", CLIENT_ID)
                        .with("redirect_uri", REDIRECT_URI)
                        .with("code", code)
                        .with("client_secret", CLIENT_SECRET))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();
    }
}