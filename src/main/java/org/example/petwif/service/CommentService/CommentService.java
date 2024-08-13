package org.example.petwif.service.CommentService;

import org.example.petwif.web.dto.CommentDto.CommentRequestDto;
import org.example.petwif.web.dto.CommentDto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    public Long writeComment(CommentRequestDto commentRequestDto, Long albumId, Long nameId);
    public List<CommentResponseDto> commentList(Long id);
    public void updateComment(CommentRequestDto commentRequestDto, Long CommentId);
    public void deleteComment(Long commentId);

}
