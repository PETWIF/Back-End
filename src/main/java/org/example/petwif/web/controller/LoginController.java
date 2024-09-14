package org.example.petwif.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.petwif.JWT.TokenDto;
import org.example.petwif.JWT.TokenProvider;
import org.example.petwif.apiPayload.ApiResponse;
import org.example.petwif.service.MemberService.MemberService;
import org.example.petwif.service.MemberService.SocialLogin.GoogleLogin.GoogleLoginService;
import org.example.petwif.service.MemberService.SocialLogin.KakaoLogin.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {

    private final GoogleLoginService googleLoginService;
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;
    private final MemberService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/code/google")
    @ResponseBody
    public ApiResponse<TokenDto> googleLogin(@RequestParam("code") String code) {
        try {
            String decode = URLDecoder.decode(code, StandardCharsets.UTF_8);
            TokenDto dto = googleLoginService.loginByGoogleAndSignUp(decode);
            return ApiResponse.onSuccess(dto);
        } catch (Exception e) {
            return ApiResponse.onFailure("400", e.getMessage(), null);
        }
    }

    @GetMapping("/oauth")
    @ResponseBody
    public ApiResponse<TokenDto> kakaoOauth(@RequestParam("code") String code) {
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
        log.info("회원 정보 입니다.{}", userInfo);
        KakaoAccount account = userInfo.getKakao_account();
        String email = account.getEmail();

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null);
        TokenDto dto = tokenProvider.generateTokenDto(authentication);
        System.out.println("accessToken 확인 : " + dto.getAccessToken());
        userService.createUser(userInfo.getKakao_account().getEmail());

        return ApiResponse.onSuccess(dto);
    }
}
