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

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {

    private final GoogleLoginService googleLoginService;
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;
    private final MemberService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/code/google")
    public ApiResponse<TokenDto> googleLogin(@RequestParam("code") String code) {
        try {
            TokenDto dto = googleLoginService.loginByGoogleAndSignUp(code);
            return ApiResponse.onSuccess(dto);
        } catch (Exception e) {
            return null;
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

        userService.createUser(userInfo.getKakao_account().getEmail());

        return ApiResponse.onSuccess(dto);
    }


//    @GetMapping("/oauth")
//    @ResponseBody
//    public String kakaoOauth(@RequestParam("code") String code) {
//        log.info("인가 코드를 이용하여 토큰을 받습니다.");
//        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
//        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);
//        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
//        log.info("회원 정보 입니다.{}",userInfo);
//
//        userService.createUser(userInfo.getKakao_account().getEmail());
//
//        return "okay";
//    }
}
//    @PostMapping("/code/kakao")
//    public ApiResponse<TokenDto> kakaoLogin(@RequestParam("code") String code) {
//        try {
//            TokenDto dto = kakaoLoginService.signup(code);
//            return ApiResponse.onSuccess(dto);
//        } catch (Exception e) {
//            return null;
//        }
//
//
//
//    }
