package org.example.petwif.repository;

import org.example.petwif.domain.entity.Block;
import org.example.petwif.domain.entity.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByMember_IdAndTarget_Id(Long memberId, Long targetId);

    Slice<Block> findAllByMemberOrderByCreatedAt(Member member, PageRequest pageRequest);

    boolean existsByMember_IdAndTarget_Id(Long memberId, Long targetId);
}
