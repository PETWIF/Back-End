package org.example.petwif.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.domain.entity.ChatRoom;
import org.example.petwif.service.ChatService.ChatQueryService;
import org.example.petwif.validation.annotation.ExistChatRoom;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatRoomExistValidator implements ConstraintValidator<ExistChatRoom, Long> {

    private final ChatQueryService chatQueryService;

    @Override
    public void initialize(ExistChatRoom constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long values, ConstraintValidatorContext context) {
        Optional<ChatRoom> target = chatQueryService.findChatRoom(values);

        if (target.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CHATROOM_NOT_FOUND.toString()).addConstraintViolation();

            return false;
        }
        return true;
    }
}
