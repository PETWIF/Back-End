package org.example.petwif.service.albumService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.albumConverter.AlbumConverter;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.AlbumLike;
import org.example.petwif.domain.entity.Comment;
import org.example.petwif.repository.AlbumRepository;
import org.example.petwif.repository.CommentRepository;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.repository.albumRepository.AlbumBookmarkRepository;
import org.example.petwif.repository.albumRepository.AlbumLikeRepository;
import org.example.petwif.web.dto.CommentDto.CommentResponseDto;
import org.example.petwif.web.dto.albumDto.AlbumResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumQueryServiceImpl implements AlbumQueryService{
    private final MemberRepository memberRepository;
    private final AlbumRepository albumRepository;
    private final CommentRepository commentRepository;
    private final AlbumLikeRepository albumLikeRepository;
    private final AlbumBookmarkRepository albumBookmarkRepository;

    @Override
    public Optional<Album> findAlbum(Long albumId){
        return albumRepository.findById(albumId);
    }


    //앨범 조회 -> 앨범 세부 페이지
   @Override
    public AlbumResponseDto.DetailResultDto getAlbumDetails(Long albumId){
        Album album =  albumRepository.findById(albumId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALBUM_NOT_FOUND));

       List<Comment> comments = commentRepository.findByAlbum(album); //댓글 리스트
       return AlbumResponseDto.DetailResultDto.builder()
               .id(album.getId())
               .title(album.getTitle())
               .content(album.getContent())
               .scope(album.getScope())
               .createdAt(album.getCreatedAt())
               .updatedAt(album.getUpdatedAt())
               .coverImage(album.getCoverImage())
               .albumImages(new ArrayList<>(album.getAlbumImages()))
               .viewCount(album.getView())
               .likeCount(albumLikeRepository.countByAlbum(album))
               .commentCount(comments.size())
               .bookmarkCount(albumBookmarkRepository.countByAlbum(album))
               .comments(comments.stream()
                       .map(c -> new CommentResponseDto(c))
                       .collect(Collectors.toList()))
               .build();
       }

       //앨범 조회 ->


}
