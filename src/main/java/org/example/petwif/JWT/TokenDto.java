package org.example.petwif.JWT;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
