package org.example.petwif.web.dto.CommentDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petwif.domain.entity.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long id;
    private String content;
    private String name;


    @Builder
    public CommentResponseDto(Comment comment) {
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.id = comment.getId();
        this.content = comment.getContent();
        this.name = comment.getMember().getUsername();
    }
}
