package org.example.petwif.repository;

import org.example.petwif.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    //Member findByEmail(String email);
    @Query("select m from Member m where m.id = :mId")
    Member findByMemberId(@Param("mId")Long id);

    @Query("select m from Member m where m.email = :mail")
    Optional<Member> findMemberByEmail(@Param("mail") String mail);
    @Query("select m from Member m where m.email = :mail and m.oauthProvider = :oauth")
    Optional<Member> checkEmail(@Param("mail") String mail, @Param("oauth") String oauth);


    @Query("select m from Member m where m.nickname = :nickname")
    Optional<Member> checkNickname(@Param("nickname") String name);

//    @Query("select m from Member m where m.oauthProvider = :oauth and m.email=:email")
//    Optional<Member> checkGoogleEmail(@Param("oauth") String oauth, @Param("email") String email);

    //@Query("delete from Member m where m.id=:id")

    @Override
    void deleteById(Long id);
}
