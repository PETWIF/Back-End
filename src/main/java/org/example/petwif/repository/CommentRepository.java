package org.example.petwif.repository;

import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByAlbum(Album album);


}
