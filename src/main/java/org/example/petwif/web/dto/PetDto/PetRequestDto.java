package org.example.petwif.web.dto.PetDto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.example.petwif.domain.enums.Gender;
import org.example.petwif.domain.enums.PetGender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDto {
    private String petName;
    private PetGender gender;  // PetGender Enum 사용
    private Integer age;       // Integer 타입으로 null 허용
    private String petKind;
    private String etc;        // etc 필드 추가
}

//@Getter
//@Builder
//public class PetRequestDto {
//    private String petName;
//
//    @Enumerated(EnumType.STRING)
//    private String gender;
//
//    private Integer age;
//    private String petKind;
//}
