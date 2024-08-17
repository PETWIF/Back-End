package org.example.petwif.service.MemberService.SocialLogin.GoogleLogin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleTokenRequest {
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;

    public static GoogleTokenRequest create(String code, String clientId, String clientSecret, String redirectUri) {
        return GoogleTokenRequest.builder()
                .code(code)
                .client_id(clientId)
                .client_secret(clientSecret)
                .redirect_uri(redirectUri)
                .build();
    }
}

