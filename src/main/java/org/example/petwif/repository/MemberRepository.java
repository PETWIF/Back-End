package org.example.petwif.repository;

import org.example.petwif.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Member findByEmail(String email);
    @Query("select m from Member m where m.id = :mId")
    Member findByMemberId(@Param("mId")Long id);

}
