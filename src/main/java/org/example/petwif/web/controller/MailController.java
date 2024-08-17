package org.example.petwif.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.ApiResponse;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.service.MemberService.MailService;
import org.example.petwif.web.dto.MemberDto.EmailRequestDto;
import org.springframework.web.bind.annotation.*;

import static org.example.petwif.apiPayload.code.status.ErrorStatus._BAD_REQUEST;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService emailVerificationService;
    private final MemberRepository memberRepository;

    /* Send Email: 인증번호 전송 버튼 click */
    @PostMapping("/signup/email")
    public ApiResponse<String> sendVerificationEmail(@RequestBody @Valid EmailRequestDto mail) {
        try{
            String email = mail.getEmail();
            emailVerificationService.sendVerificationEmail(email);
            return ApiResponse.onSuccess("Verification code sent to " + email);
        } catch (Exception e){
            throw new GeneralException(_BAD_REQUEST);
        }

    }

    @PostMapping("/verify")
    public ApiResponse<String> verifyCode(@RequestParam String email, @RequestParam String code) {
        System.out.println("메일 검증 ");
        boolean isVerified = emailVerificationService.verifyCode(email, code);
        if (isVerified) {
            return ApiResponse.onSuccess("Email verified successfully");
        } else {
            return ApiResponse.onFailure("400", "Email verify failed", code+" is not correct");
        }
    }
}

