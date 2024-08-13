package org.example.petwif.repository;

import org.example.petwif.domain.entity.ChatRoom;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.entity.MemberChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {

    Optional<MemberChatRoom> findAllByMemberAndChatRoom(Member member, ChatRoom chatRoom);
}
