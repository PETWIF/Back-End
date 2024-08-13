package org.example.petwif.repository;

import org.example.petwif.domain.entity.Comment;
import org.example.petwif.domain.entity.CommentLike;
import org.example.petwif.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentIdAndMemberId(Long commentId, Long id);
}
