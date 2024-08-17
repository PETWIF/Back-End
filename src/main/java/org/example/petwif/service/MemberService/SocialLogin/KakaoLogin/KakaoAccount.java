package org.example.petwif.service.MemberService.SocialLogin.KakaoLogin;

import lombok.Data;

@Data
public class KakaoAccount {
    private Boolean has_email;
    private Boolean email_needs_agreement;
    private Boolean is_email_valid;
    private Boolean is_email_verified;
    private String email;
    // 추가함
    private String profile_image;
    private String profile_nickname;

}