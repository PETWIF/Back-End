package org.example.petwif.repository.albumRepository;

import org.example.petwif.domain.entity.AlbumImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumImageRepository extends JpaRepository<AlbumImage, Long> {
    List<AlbumImage> findAlbumImageByAlbumId(Long albumId);
}
