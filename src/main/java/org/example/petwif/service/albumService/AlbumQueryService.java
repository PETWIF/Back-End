package org.example.petwif.service.albumService;

import org.example.petwif.domain.entity.Album;
import org.example.petwif.web.dto.albumDto.AlbumResponseDto;

import java.util.Optional;

public interface AlbumQueryService {
    Optional<Album> findAlbum(Long AlbumId);

    public AlbumResponseDto.DetailResultDto getAlbumDetails(Long albumId);

}
