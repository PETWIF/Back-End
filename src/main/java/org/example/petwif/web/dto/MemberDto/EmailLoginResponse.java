package org.example.petwif.web.dto.MemberDto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailLoginResponse {
    private Long id;
    @Email
    private String email;
}
