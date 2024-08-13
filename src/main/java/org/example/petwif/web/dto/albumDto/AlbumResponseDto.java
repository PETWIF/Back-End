package org.example.petwif.web.dto.albumDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petwif.domain.entity.AlbumImage;
import org.example.petwif.domain.enums.Scope;
import org.example.petwif.web.dto.CommentDto.CommentResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;


public class AlbumResponseDto {


    //==앨범 CRUD==//
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveResultDto{
        Long memberId;
        LocalDateTime createdAt;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResultDto{
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailResultDto {
        private Long id;
        private String title;
        private String content;
        private Scope scope;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private AlbumImage coverImage;
        private List<AlbumImage> albumImages;
        private int viewCount;
        private int likeCount;
        private int commentCount;
        private int bookmarkCount;
        private List<CommentResponseDto> comments;
    }

    //==앨범 좋아요==//
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResultDto{
        private Long memberId;
        private String memberName;
        private String profileUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeListDto{
        private List<LikeResultDto> likes;
    }

    //==앨범 북마크==//
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookmarkResultDto{
        private Long memberId;
        private String memberName;
        private String profileUrl;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookmarkListDto{
        private List<BookmarkResultDto> bookmarks;
    }

    //==앨범 신고==//




}