package org.example.petwif.repository;

import org.example.petwif.domain.entity.ChatRoom;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.enums.ChatRoomStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByMemberIdAndOtherId(Long memberId, Long otherId); //채팅방 중복 생성 방지

    Optional<ChatRoom> findChatRoomById(Long id);

    Slice<ChatRoom> findAllByMember(Member member, PageRequest pageRequest); //채팅방 목록 조회
    Slice<ChatRoom> findAllByOther(Member other, PageRequest pageRequest);

    Optional<ChatRoom> findAllByMemberIdAndId(Long memberId, Long chatRoomId);
}
