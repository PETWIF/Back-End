package org.example.petwif.web.controller;

import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.service.CommentService.CommentServiceImpl;
import org.example.petwif.web.dto.CommentDto.CommentRequestDto;
import org.example.petwif.web.dto.CommentDto.CommentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("//comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    // 댓글 작성
    @PostMapping("/albums/{albumId}/commment")
    public ResponseEntity<Long> writeComment(
            @PathVariable Long albumId,
            @RequestBody CommentRequestDto commentRequestDto,
            @RequestParam Long nameId) {
        try {
            Long commentId = commentService.writeComment(commentRequestDto, albumId, nameId);
            return ResponseEntity.ok(commentId);
        } catch (GeneralException e) {
            return ResponseEntity.status(e.getErrorStatus().getHttpStatus()).build();
        }
    }

    // 특정 앨범에 대한 댓글 목록 조회
    @GetMapping("/albums/{albumId}/commment")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByAlbum(@PathVariable Long albumId) {
        try {
            List<CommentResponseDto> comments = commentService.commentList(albumId);
            return ResponseEntity.ok(comments);
        } catch (GeneralException e) {
            return ResponseEntity.status(e.getErrorStatus().getHttpStatus()).build();
        }
    }

    // 댓글 수정
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto) {
        try {
            commentService.updateComment(commentRequestDto, commentId);
            return ResponseEntity.ok().build();
        } catch (GeneralException e) {
            return ResponseEntity.status(e.getErrorStatus().getHttpStatus()).build();
        }
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok().build();
        } catch (GeneralException e) {
            return ResponseEntity.status(e.getErrorStatus().getHttpStatus()).build();
        }
    }

    // 댓글 좋아요 추가
    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<Void> likeComment(
            @PathVariable Long commentId,
            @RequestParam Long memberId) {
        try {
            commentService.likeComment(commentId, memberId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (GeneralException e) {
            return ResponseEntity.status(e.getErrorStatus().getHttpStatus()).build();
        }
    }

    // 댓글 좋아요 제거
    @DeleteMapping("/comment/{commentId}/like")
    public ResponseEntity<Void> unlikeComment(
            @PathVariable Long commentId,
            @RequestParam Long memberId) {
        try {
            commentService.unlikeComment(commentId, memberId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (GeneralException e) {
            return ResponseEntity.status(e.getErrorStatus().getHttpStatus()).build();
        }
    }
}
