package org.example.petwif.web.dto.MemberDto;

import lombok.Builder;
import lombok.Getter;
import org.example.petwif.domain.entity.Member;

@Builder
@Getter
public class EmailSignupRequestDTO {
    private String name;
    private String email;
    private String pw;
    private String pw_check;

    public Member toEntity(String password){
        return Member.builder()
                .username(this.name)
                .email(this.email)
                .pw(password)
                .build();
    }
}
