package org.example.petwif.service.ChatService;

import org.example.petwif.domain.entity.Chat;
import org.example.petwif.domain.entity.ChatRoom;
import org.example.petwif.web.dto.ChatDTO.ChatRequestDTO;

public interface ChatCommandService {

    //채팅 생성
    ChatRoom createChatRoom(Long memberId, Long otherId, ChatRequestDTO.CreateChatRoomDTO request);

    //채팅 보내기
    Chat sendChat(Long memberId, Long chatRoomId, ChatRequestDTO.SendChatDTO request);

    //채팅방 나가기 = memberChatRoom
    void deleteChatRoom(Long memberId, Long chatRoomId);
}
