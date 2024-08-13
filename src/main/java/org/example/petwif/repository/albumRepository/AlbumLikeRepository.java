package org.example.petwif.repository.albumRepository;

import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.AlbumLike;
import org.example.petwif.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumLikeRepository extends JpaRepository<AlbumLike, Long> {
    Optional<AlbumLike> findByAlbumAndMember(Album album, Member member); //예외 처리를 위해 추가
    int countByAlbum(Album album);

    List<AlbumLike> findByAlbum(Album album);
}
