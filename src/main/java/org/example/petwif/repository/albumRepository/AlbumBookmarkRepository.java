package org.example.petwif.repository.albumRepository;

import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.AlbumBookmark;
import org.example.petwif.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumBookmarkRepository extends JpaRepository<AlbumBookmark, Long> {
    int countByAlbum(Album album);

    List<AlbumBookmark> findByAlbum(Album album);
    Optional<AlbumBookmark> findByAlbumIdAndMemberId(Long albumId, Long memberId);

    Optional<AlbumBookmark> findByAlbumAndMember(Album album, Member member);
}
