package org.example.petwif.service.MemberService.SocialLogin.KakaoLogin;

import lombok.Data;

@Data
public class KakaoUserInfoResponse {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;
}