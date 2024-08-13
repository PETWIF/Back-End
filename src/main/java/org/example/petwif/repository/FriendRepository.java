package org.example.petwif.repository;

import org.example.petwif.domain.entity.Friend;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.enums.FriendStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByMember_IdAndFriend_Id(Long memberId, Long friendId);

    Slice<Friend> findAllByMemberAndStatusOrderByCreatedAt(Member member, FriendStatus status, PageRequest pageRequest);
}
