package org.example.petwif.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.JWT.JwtToken;
import org.example.petwif.JWT.JwtTokenProvider;
import org.example.petwif.JWT.JwtUtil;
import org.example.petwif.apiPayload.ApiResponse;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.enums.Gender;
import org.example.petwif.domain.enums.Telecom;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.web.dto.MemberDto.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Boolean EmailSignup(EmailSignupRequestDTO dto) {
        // 동일한 이메일로 회원가입 안 됨, Optional<Member>와 isPresent()로 존재여부 찾아내기
        if (memberRepository.checkEmail(dto.getEmail()).isPresent()) {
            // 중복된 이메일 존재
            return false;  // false 반환으로 중복된 이메일임을 알림
        }

        String pw1 = dto.getPw();
        String pw2 = dto.getPw_check();

        if (!pw1.equals(pw2)) {
            // 비밀번호가 일치하지 않음
            throw new IllegalArgumentException("Passwords do not match.");
        }

        Member member = new Member();
        member.setUsername(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPw(encoder.encode(pw1));
        memberRepository.save(member);

        return true;
    }
/*
    @Transactional
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }
 */
    public ApiResponse<JwtToken> login(LoginRequestDto dto){
        String clientEmail = dto.getEmail();
        String clientPw = dto.getPw();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(clientEmail, clientPw);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        if (memberRepository.checkEmail(clientEmail).isPresent()){
            Member member = memberRepository.findByEmail(clientEmail);
            String dbPw = member.getPw();
            if (encoder.matches(clientPw,dbPw)){
                return ApiResponse.onSuccess(jwtToken);
            }
            else {
                return ApiResponse.onFailure("400", "로그인 실패", jwtToken );
            }
        }
        else return ApiResponse.onFailure("400", "회원이 아닙니다.", jwtToken);

    }
//    public ApiResponse<String> login(LoginRequestDto dto){
//        String clientEmail = dto.getEmail();
//        String clientPw = dto.getPw();
//        if (memberRepository.checkEmail(clientEmail).isPresent()){
//            Member member = memberRepository.findByEmail(clientEmail);
//            String dbPw = member.getPw();
//            if (encoder.matches(clientPw,dbPw)){
//                String token = JwtUtil.generateToken(clientEmail);
//                return ApiResponse.onSuccess(token);
//            }
//            else {
//               return ApiResponse.onFailure("400", "로그인 실패", "비밀번호가 틀렸습니다.");
//            }
//        }
//        else return ApiResponse.onFailure("400", "회원이 아닙니다.", "회원이 아닙니다. 회원가입을 해주세요.");
//
//    }


    public Boolean checkNickName(Long mId, NicknameDto nickname){
        if (memberRepository.checkNickname(nickname.getNickname()).isPresent()) {
            // 중복된 닉네임 존재
            return false;  // false 반환으로 중복된 이메일임을 알림
        }
        // 닉네임 중복이 아니면 세팅
        else{
            Member member = memberRepository.findByMemberId(mId);
            if (nickname.getNickname()!=null) member.setNickname(nickname.getNickname());
            memberRepository.save(member);
            return true;
        }
    }

    public boolean MemberInfoAdd(Long memberId, MemberEtcInfoRequestDto dto) {
        Member member = memberRepository.findByMemberId(memberId);
        if (member == null) {
            // 회원을 찾을 수 없을 때 false 반환
            return false;
        }

        if (dto.getGender() != null) {
            member.setGender(Gender.valueOf(dto.getGender()));
        }
        if (dto.getBirthDate() != null) {
            member.setBirthDate(dto.getBirthDate());
        }
        if (dto.getTelecom() != null) {
            member.setTelecom(Telecom.valueOf(dto.getTelecom()));
        }
        if (dto.getPhoneNum() != null) {
            member.setPhoneNumber(dto.getPhoneNum());
        }
        if (dto.getAddress() != null) {
            member.setAddress(dto.getAddress());
        }

        memberRepository.save(member);
        // 성공적으로 업데이트한 경우 true 반환
        return true;
    }


    public Boolean changePassword(Long id, PasswordChangeRequestDto dto){
        Member member = memberRepository.findByMemberId(id);

        String pw1 = dto.getChangePW();
        String pw2 = dto.getCheckChangePw();

        if (pw1.equals(pw2)){
            member.setPw(encoder.encode(pw1));
            memberRepository.save(member);
            return true;
        }  else{
            return false;
        }
    }
}
