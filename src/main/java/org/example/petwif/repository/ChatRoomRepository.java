package org.example.petwif.repository;

import org.example.petwif.domain.entity.ChatRoom;
import org.example.petwif.domain.entity.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findChatRoomById(Long id);

    Slice<ChatRoom> findAllByMember(Member member, PageRequest pageRequest); //채팅방 목록 조회

    Optional<ChatRoom> findAllByMemberIdAndId(Long memberId, Long chatRoomId);
}
