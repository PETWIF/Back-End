package org.example.petwif.service.ChatService;

import org.example.petwif.domain.entity.Chat;
import org.example.petwif.domain.entity.ChatRoom;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface ChatQueryService {

    //채팅창 화면 조회 (채팅 내용 목록 조회)
    Optional<Chat> findChat(Long id);

    Slice<Chat> getChatList(Long chatRoomId, Integer page);

    //채팅방 목록 조회
    Optional<ChatRoom> findChatRoom(Long id);

    Slice<ChatRoom> getChatRoomList(Long memberId, Integer page);

    //가장 최근 채팅 조회
    Optional<Chat> getLastChat(Long id);
}


