package org.example.petwif.albumRepository;

import org.example.petwif.domain.entity.AlbumBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumBookmarkRepository extends JpaRepository<AlbumBookmark, Long> {
}
