package org.example.petwif.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.JWT.TokenDto;
import org.example.petwif.JWT.TokenProvider;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.enums.Gender;
import org.example.petwif.domain.enums.Telecom;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.web.dto.MemberDto.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    @Transactional   // 이메일로 회원가입할 때 멤버 생성자
    public EmailLoginResponse EmailSignup(EmailSignupRequestDTO dto) {
        // 동일한 이메일로 회원가입 안 됨, Optional<Member>와 isPresent()로 존재여부 찾아내기
        if (memberRepository.findMemberByEmail(dto.getEmail()).isPresent()) {
        //if (memberRepository.checkEmail(dto.getEmail(), "PETWIF").isPresent()) {
            // 중복된 이메일 존재
            return null;
        }

        String pw1 = dto.getPw();
        String pw2 = dto.getPw_check();

        if (!pw1.equals(pw2)) {
            // 비밀번호가 일치하지 않음
            throw new IllegalStateException("Passwords do not match.");
        }

        else {
            Member member = new Member();
            member.setName(dto.getName());
            member.setEmail(dto.getEmail());
            member.setPw(encoder.encode(pw1));
            member.setOauthProvider("PETWIF");
            // 여기에 추가로 스티커 로직 구현
            memberRepository.save(member);
            return mapMemberToResponse(member);
        }
    }

    public TokenDto login(LoginRequestDto dto) {
        String clientEmail = dto.getEmail();
        String clientPw = dto.getPw();

        Member member = memberRepository.checkEmail(clientEmail, "PETWIF")
                .orElseThrow(() -> new IllegalArgumentException("회원이 아닙니다. 회원가입을 해주세요."));

        if (!encoder.matches(clientPw, member.getPw())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        // 사용자 인증에 성공하면 JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(clientEmail, null);
        return tokenProvider.generateTokenDto(authentication);
    }

    public EmailLoginAccessTokenResponse EmailLogin(LoginRequestDto dto){

        String email = dto.getEmail();
        TokenDto token = login(dto);
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member with email " + email + " not found."));
        EmailLoginAccessTokenResponse result = mapMemberToEmailResponse(token, member);
        return result;
    }
    public EmailLoginAccessTokenResponse mapMemberToEmailResponse(TokenDto dto, Member member) {
        return EmailLoginAccessTokenResponse.builder()
                .accessToken(dto.getAccessToken())
                .refreshToken(dto.getRefreshToken())
                .id(member.getId())
                .name(member.getName())
                .profile_url(member.getProfile_url())
                .nickname(member.getNickname())
                .build();
    }

    @Transactional(readOnly = true)
    public Member getMemberByToken(String token) {
        // 토큰이 유효한지 검증
        if (!tokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        // 토큰에서 인증 정보를 추출
        Authentication authentication = tokenProvider.getAuthentication(token);

        // 인증 정보에서 사용자 이메일을 가져와 회원 조회
        String email = authentication.getName();
        System.out.println("회원조회 체크 "+email);

        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Member getMemberByNickname(String nickname) {

        if (!memberRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다.");
        }

        return memberRepository.findByNickname(nickname);
    }

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
        return true;
    }


    public Boolean changePassword(String email, PasswordChangeRequestDto dto){
        // 비밀번호 변경은 이메일로 로그인했을때만 가능하니까 checkEmail 서비스 사용함

        Member member = memberRepository.checkEmail(email, "PETWIF")
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        if (member.getOauthProvider().equals("KAKAO") || member.getOauthProvider().equals("GOOGLE")){
            throw new IllegalStateException("소셜 로그인으로 이미 가입된 상태입니다. 소셜로그인으로 접속해주세요");
        }

        String pw1 = dto.getChangePW();
        String pw2 = dto.getCheckChangePw();

        if (pw1.equals(pw2)){
            member.setPw(encoder.encode(pw1));
            memberRepository.save(member);
            return true;
        }  else {
            return false;
        }

    }

    public void uploadProfile(Long mId, String image) {
        Member member = memberRepository.findByMemberId(mId);
        member.setProfile_url(image);
        memberRepository.save(member);
    }

    public EmailLoginResponse mapMemberToResponse(Member member) {
        return EmailLoginResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }

    public MemberInfoResponseDto mapMemberInfoToResponse(Member member){

        return MemberInfoResponseDto.builder()
                .id(member.getId())
                .profile_url(member.getProfile_url())
                .name(member.getName())
                .nickname(member.getNickname())
                .build();
    }

    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

    @Transactional   // 카카오 회원가입 할 때의 멤버 생성자
    public Long createUser(String email, String profile, String nickname) {
        Member user = Member.builder()
                .email(email)
                .oauthProvider("KAKAO")
                .name(getUsernameFromEmail(email))
                .nickname(getUsernameFromEmail(email))
                .profile_url(profile)
                .build();
        if (memberRepository.findMemberByEmail(email).isEmpty()){
            memberRepository.save(user);
        }
        log.info("nickname "+user.getName()+", profileUrl "+ user.getProfile_url());

        return user.getId();
    }

    public static String getUsernameFromEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            throw new IllegalArgumentException("유효하지 않은 이메일 주소입니다.");
        }
    }



}
