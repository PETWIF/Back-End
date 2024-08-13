package org.example.petwif.web.dto.ChatDTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    // 메시지  타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType messageType; // 메시지 타입
    private Long chatRoomId; // 방 번호
    private Long memberId; // 채팅을 보낸 사람
    private String content; // 메시지
}
