package org.example.petwif.web.dto.ChatDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ChatRequestDTO {

    @Getter
    public static class CreateChatRoomDTO { //채팅 생성
        @NotNull
        private Long memberId;
        @NotNull
        private Long otherId;
    }

    @Getter
    public static class SendChatDTO { //채팅 보내기
        @NotBlank
        private String content;
    }
}