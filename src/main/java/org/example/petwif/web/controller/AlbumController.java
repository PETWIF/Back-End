package org.example.petwif.web.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.petwif.albumConverter.AlbumConverter;
import org.example.petwif.apiPayload.ApiResponse;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.AlbumLike;
import org.example.petwif.service.albumService.*;
import org.example.petwif.validation.annotation.ExistAlbum;
import org.example.petwif.validation.annotation.ExistMember;
import org.example.petwif.web.dto.albumDto.AlbumRequestDto;
import org.example.petwif.web.dto.albumDto.AlbumResponseDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class AlbumController {
    private final AlbumLikeService albumLikeService;
    private final AlbumService albumService;
    private final AlbumQueryService albumQueryService;
    private final AlbumBookmarkService albumBookmarkService;
    private final AlbumReportService albumReportService;


    //==앨범 생성==//
    @PostMapping("/albums")
    @Operation(summary = "앨범 생성 API", description = "앨범을 생성하는 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<AlbumResponseDto.SaveResultDto> createAlbum(@RequestBody AlbumRequestDto.SaveRequestDto requestDto) {
        Album album = albumService.saveAlbum(requestDto);
        AlbumResponseDto.SaveResultDto saveResultDto = AlbumConverter.toAlbumResultDto(album);
        return ApiResponse.onSuccess(saveResultDto);
    }


    //==앨범 수정==//
    @PatchMapping("/albums/{albumId}")
    @Operation(summary = "앨범 수정 API", description = "앨범을 생성 후 수정하는 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<AlbumResponseDto.UpdateResultDto> updateAlbum(@ExistAlbum @PathVariable Long albumId, @ExistMember @RequestParam Long memberId, @RequestBody AlbumRequestDto.UpdateRequestDto requestDto) {
        Album updatedAlbum = albumService.updateAlbum(albumId, memberId, requestDto);
        return ApiResponse.onSuccess(AlbumConverter.UpdatedAlbumResultDto(updatedAlbum));
    }


    //==앨범 삭제==//
    @DeleteMapping("/albums/{albumId}")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 삭제 API", description = "앨범을 삭제하는 API 입니다.")
    public ApiResponse<Void> deleteAlbum(@ExistAlbum @PathVariable("albumId") Long albumId, @ExistMember @RequestParam Long memberId) {
        albumService.deleteAlbum(albumId, memberId);
        return ApiResponse.onSuccess(null);
    }


    //==앨범 조회==//


    // 특정 사용자의 특정 앨범 조회, albumId로 조회, 앨범 상세 페이지
    @GetMapping("/albums/{albumId}")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 상세 페이지 조회 API", description = "특정 앨범에 들어갔을 때 앨범 상세 페이지를 보는 API입니다.")
    public ApiResponse<AlbumResponseDto.DetailResultDto> getAlbumDetail(@ExistAlbum @PathVariable Long albumId, @ExistMember @RequestParam Long memberId){

        albumService.increaseView(albumId, memberId);
        AlbumResponseDto.DetailResultDto albumDetail = albumQueryService.getAlbumDetails(albumId);
        return ApiResponse.onSuccess(albumDetail);

    }


    // 특정 사용자의


    //==앨범 좋아요==//
    @PostMapping("/albums/{albumId}/like")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 좋아요 API", description = "앨범 좋아요를 누르는 API 입니다. 앨범에서 좋아요 리스트를 누르면 볼수 있습니다.")
    public ApiResponse<Void> createAlbumLike(@ExistAlbum @PathVariable Long albumId, @RequestBody AlbumRequestDto.LikeRequestDto requestDto){
        albumLikeService.addAlbumLike(albumId, requestDto.getMemberId());
        return ApiResponse.onSuccess(null);
    }


    //==앨범 좋아요 취소==//
    @DeleteMapping("/albums/{albumId}/like")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 좋아요 취소 API", description = "앨범 좋아요를 취소하는 API 입니다. 좋아요가 없다면 에러")
    public ApiResponse<Void> deleteAlbumLike(@ExistAlbum @PathVariable Long albumId, @RequestBody AlbumRequestDto.LikeRequestDto requestDto){
        albumLikeService.deleteAlbumLike(albumId, requestDto.getMemberId());
        return ApiResponse.onSuccess(null);
    }

    //==앨범 좋아요 리스트 조회==//
    @GetMapping("/albums/{albumId}/like")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 좋아요 리스트 조회 API", description = "특정 앨범에 대해 좋아요를 누른 사람의 목록이 나오는 API 입니다. 좋아요가 없다면 에러")
    public ApiResponse<AlbumResponseDto.LikeListDto> getAlbumList(@ExistAlbum @PathVariable Long albumId){
        List<AlbumResponseDto.LikeResultDto> likes  = albumLikeService.getAlbumLikes(albumId);
        AlbumResponseDto.LikeListDto likeListDto = new AlbumResponseDto.LikeListDto(likes);
        return ApiResponse.onSuccess(likeListDto);
    }

    //==앨범 북마크==//
    @PostMapping("/albums/{albumId}/bookmark")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 북마크 API", description = "앨범 북마크를 누르는 API입니다. 나의 북마크 에서 확인 가능")
    public ApiResponse<Void> creatAlbumBookmark(@ExistAlbum @PathVariable Long albumId, @ExistMember @RequestParam Long memberId) {
        try {
            albumBookmarkService.addBookmark(albumId, memberId);
            return ApiResponse.onSuccess(null);
        } catch (GeneralException ex) {
            return ApiResponse.onFailure("ERROR", ex.getMessage(), null);
        } catch (IllegalStateException ex) {
            return ApiResponse.onFailure("ALREADY_BOOKMARKED", "이미 북마크에 추가되었습니다.", null);
        }
    }
    //==앨범 북마크 취소==//
    @DeleteMapping("/albums/{albumId}/bookmark")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 북마트 취소 API", description = "앨범 북마크를 취소하는 API입니다.")
    public ApiResponse<Void> deleteAlbumBookmark(@ExistAlbum @PathVariable Long albumId, @ExistMember @RequestParam Long memberId ){
        try {
            albumBookmarkService.deleteBookmark(albumId, memberId);
            return ApiResponse.onSuccess(null);
        } catch (GeneralException e) {
            return ApiResponse.onFailure("ERROR", e.getMessage(), null);
        } catch (IllegalStateException e) {
            return ApiResponse.onFailure("ALREADY_BOOKMARKED", "이미 북마크에 추가되었습니다.", null);
        }
    }

    //==앨범 북마크 조회==//
    @GetMapping("/albums/{albumId}/bookmark")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 북마크 리스트 조회 API", description = "앨범내애 북마크 버튼을 눌렀을 때 북마크 목록이 나오는 API 입니다")
    public ApiResponse<AlbumResponseDto.BookmarkListDto> getBookmarkList(@ExistAlbum @PathVariable Long albumId){
        List<AlbumResponseDto.BookmarkResultDto> bookmarks = albumBookmarkService.getAlbumBookmarks(albumId);
        AlbumResponseDto.BookmarkListDto bookmarkListDto = new AlbumResponseDto.BookmarkListDto(bookmarks);
        return ApiResponse.onSuccess(bookmarkListDto);
    }


    //== 앨범 신고==//
    @PostMapping("/albums/{albumId}/report")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Operation(summary = "앨범 신고 API", description = "특정 앨범을 신고하는 API입니다.")
    public ApiResponse<Void> creatAlbumReport(@ExistAlbum @PathVariable Long albumId, @ExistMember @RequestParam Long memberId, @RequestBody AlbumRequestDto.ReportRequestDto requestDto){
        albumReportService.doReport(albumId, memberId, requestDto.getReportReason());
        return ApiResponse.onSuccess(null);
    }



}
