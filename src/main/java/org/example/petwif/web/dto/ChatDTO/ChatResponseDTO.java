package org.example.petwif.web.dto.ChatDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petwif.domain.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public class ChatResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateChatRoomResultDTO { //채팅 생성
        private Long chatRoomId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendChatResultDTO { //채팅 보내기
        private Long chatId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatPreviewDTO { //채팅창 화면 조회
        private Long chatId;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatPreviewListDTO{
        List<ChatResponseDTO.ChatPreviewDTO> chatList;
        private Integer listSize;
        private boolean isFirst;
        private boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomPreviewDTO{ //채팅방 목록 조회
        private Long memberId;
        private Long otherId;
        private Long chatRoomId;
        private String roomName;
        private LocalDateTime createdAt;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomPreviewListDTO {
        List<ChatResponseDTO.ChatRoomPreviewDTO> chatRoomList;
        private Integer listSize;
        private boolean isFirst;
        private boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LastChatPreviewDTO { //가장 최근 채팅 조회
        private Long chatRoomId;
        private Long memberId;
        private String content;
        private LocalDateTime time;
    }
}
