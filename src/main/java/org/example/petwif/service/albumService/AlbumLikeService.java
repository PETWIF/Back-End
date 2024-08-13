package org.example.petwif.service.albumService;

import org.example.petwif.domain.entity.AlbumImage;
import org.example.petwif.domain.entity.AlbumLike;
import org.example.petwif.web.dto.albumDto.AlbumResponseDto;

import java.util.List;

public interface AlbumLikeService {
    public void addAlbumLike(Long albumId, Long memberId);

    public void deleteAlbumLike(Long albumId, Long memberId);

    public List<AlbumResponseDto.LikeResultDto> getAlbumLikes(Long albumId);
}
