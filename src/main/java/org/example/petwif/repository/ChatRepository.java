package org.example.petwif.repository;

import org.example.petwif.domain.entity.Chat;
import org.example.petwif.domain.entity.ChatRoom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findChatById(Long id);

    Slice<Chat> findAllByChatRoom(ChatRoom chatRoom, PageRequest pageRequest); //채팅창 화면 조회

    Optional<Chat> findTopByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId); //가장 최근 채팅 조회
}
