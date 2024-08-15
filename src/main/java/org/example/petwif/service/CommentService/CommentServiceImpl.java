package org.example.petwif.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.domain.entity.Album;
import org.example.petwif.domain.entity.Comment;
import org.example.petwif.domain.entity.CommentLike;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.repository.AlbumRepository;
import org.example.petwif.repository.CommentLikeRepository;
import org.example.petwif.repository.CommentRepository;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.web.dto.CommentDto.CommentRequestDto;
import org.example.petwif.web.dto.CommentDto.CommentResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final AlbumRepository albumRepository;
    private final CommentLikeRepository commentLikeRepository;


    @Override
    // 댓글 또는 대댓글 작성 메서드
    public Long writeComment(CommentRequestDto commentRequestDto, Long albumId, Long memberId, Long parentCommentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALBUM_NOT_FOUND));

        Comment comment;

        if (parentCommentId == null) {
            // 일반 댓글 작성
            comment = Comment.builder()
                    .content(commentRequestDto.getContent())
                    .album(album)
                    .member(member)
                    .likeCount(0)  // 초기 좋아요 수 0으로 설정
                    .build();
        } else {
            // 대댓글 작성
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

            comment = Comment.builder()
                    .content(commentRequestDto.getContent())
                    .album(album)
                    .member(member)
                    .parentComment(parentComment)
                    .likeCount(0)  // 초기 좋아요 수 0으로 설정
                    .build();

            parentComment.addChildComment(comment);
        }

        commentRepository.save(comment);
        return comment.getId();
    }


    @Override
    public List<CommentResponseDto> commentList(Long id){
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ALBUM_NOT_FOUND));
        List<Comment> commentList=commentRepository.findByAlbum(album);

        return commentList.stream()
                .map(comment-> CommentResponseDto.builder()
                        .comment(comment)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void updateComment(CommentRequestDto commentRequestDto, Long CommentId){
        Comment comment = commentRepository.findById(CommentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus._BAD_REQUEST));
        comment.update(commentRequestDto.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }

    public CommentLike likeComment(Long commentId, Long memberId) {
        // 댓글이 존재하는지 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        // 멤버가 존재하는지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 이미 좋아요를 누른 경우 예외 처리
        Optional<CommentLike> existingLike = commentLikeRepository.findByCommentIdAndMemberId(commentId, memberId);
        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("You have already liked this comment.");
        }

        // 좋아요 추가 및 좋아요 수 증가
        CommentLike commentLike = new CommentLike(comment, member);
        comment.incrementLikeCount();  // 좋아요 수 증가
        commentRepository.save(comment);  // 변경된 likeCount 저장
        return commentLikeRepository.save(commentLike);
    }

    public void unlikeComment(Long commentId, Long memberId) {
        // 좋아요 찾기
        CommentLike commentLike = commentLikeRepository.findByCommentIdAndMemberId(commentId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));

        // 좋아요 삭제 및 좋아요 수 감소
        Comment comment = commentLike.getComment();
        comment.decrementLikeCount();  // 좋아요 수 감소
        commentRepository.save(comment);  // 변경된 likeCount 저장
        commentLikeRepository.delete(commentLike);
    }
}
