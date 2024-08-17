package org.example.petwif.web.dto.MemberDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.example.petwif.domain.entity.Member;

@Builder
@Getter
public class EmailSignupRequestDTO {
    private String name;
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
    @Size(min = 12, message = "비밀번호는 최소 12자 이상이어야 합니다.")
    private String pw;
    @Size(min = 12, message = "비밀번호는 최소 12자 이상이어야 합니다.")
    private String pw_check;
}
