package org.example.petwif.web.dto.MemberDto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
}
