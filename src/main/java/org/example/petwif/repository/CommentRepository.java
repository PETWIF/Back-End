package org.example.petwif.repository;

import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByAlbum(Album album);
}
