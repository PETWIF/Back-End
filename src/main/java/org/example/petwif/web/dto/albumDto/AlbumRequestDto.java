package org.example.petwif.web.dto.albumDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petwif.domain.entity.AlbumImage;
import org.example.petwif.domain.enums.Scope;

import java.util.List;

@Builder
public class AlbumRequestDto {
    //==앨범 CRUD==//
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveRequestDto{
        private Long memberId;
        private String title;
        private String content;
        private Scope scope;
        private AlbumImage coverImage;
        private List<AlbumImage> albumImages;  // 이미지 URL 목록
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequestDto {
        private String title;
        private String content;
        private Scope scope;
        private AlbumImage coverImage;
        private List<AlbumImage> newAlbumImages; // 새 이미지
    }


    //==앨범 좋아요==//
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeRequestDto{
        private Long memberId;
    }


    //==앨범 북마크==//
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookmarkRequestDto{
        private Long memberId;
    }

    //==앨범 신고 사유 ==//

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportRequestDto{
        private String reportReason;
    }
}
