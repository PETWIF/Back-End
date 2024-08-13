package org.example.petwif.web.dto.BlockDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class BlockResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlockResultDTO {
        Long id;
        Long memberId;
        Long targetId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlockListDTO {
        List<BlockResultDTO> blockList;
        Integer listSize;
        Boolean isFirst;
        Boolean isLast;
        Boolean hasNext;
    }
}
