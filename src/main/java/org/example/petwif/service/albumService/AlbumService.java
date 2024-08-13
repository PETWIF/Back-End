package org.example.petwif.service.albumService;

import org.example.petwif.domain.entity.Album;
import org.example.petwif.web.dto.albumDto.AlbumRequestDto;
import org.example.petwif.web.dto.albumDto.AlbumResponseDto;

public interface AlbumService {
    Album saveAlbum(AlbumRequestDto.SaveRequestDto requestDto);
    Album updateAlbum(Long albumId, Long memberId, AlbumRequestDto.UpdateRequestDto requestDto);
    void deleteAlbum(Long albumId, Long memberId);
    void increaseView(Long albumId, Long memberId);
}