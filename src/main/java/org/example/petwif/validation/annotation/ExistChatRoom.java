package org.example.petwif.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.petwif.validation.validator.ChatRoomExistValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ChatRoomExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistChatRoom {

    String message() default "해당하는 채팅방이 존재하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
