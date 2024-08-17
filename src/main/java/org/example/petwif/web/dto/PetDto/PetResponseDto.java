package org.example.petwif.web.dto.PetDto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.example.petwif.domain.enums.PetGender;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {
    private Long petId;
    private String petName;
    private PetGender gender;  // PetGender Enum 사용
    private Integer age;           // 기본형 int 사용
    private String petKind;
    private String etc;        // etc 필드 추가
}

//@Builder
//public class PetResponseDto {
//    private Long petId;
//    private String petName;
//    @Enumerated(EnumType.STRING)
//    private PetGender gender;
//    private int age;
//    private String petKind;
//}
