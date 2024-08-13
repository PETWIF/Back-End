package org.example.petwif.albumConverter;

import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.Comment;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.repository.CommentRepository;
import org.example.petwif.repository.albumRepository.AlbumBookmarkRepository;
import org.example.petwif.repository.albumRepository.AlbumLikeRepository;
import org.example.petwif.web.dto.CommentDto.CommentResponseDto;
import org.example.petwif.web.dto.albumDto.AlbumRequestDto;
import org.example.petwif.web.dto.albumDto.AlbumResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumConverter {


    public static AlbumResponseDto.SaveResultDto toAlbumResultDto(Album album){
        return AlbumResponseDto.SaveResultDto.builder()
                .memberId(album.getMember().getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AlbumResponseDto.UpdateResultDto UpdatedAlbumResultDto(Album album){
        return AlbumResponseDto.UpdateResultDto.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public static Album toAlbumEntity(AlbumRequestDto.SaveRequestDto requestDto, Member member) {
        return Album.builder()
                .member(member)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .scope(requestDto.getScope())
                .view(0)
                .coverImage(requestDto.getCoverImage())
                .albumImages(requestDto.getAlbumImages())
                .build();
    }

}
