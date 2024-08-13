package org.example.petwif.web.dto.CommentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeRequestDto {

    private Long memberId;
    private Long commentId;

}
