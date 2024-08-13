package org.example.petwif.converter;

import org.example.petwif.domain.entity.Block;
import org.example.petwif.web.dto.BlockDTO.BlockResponseDTO;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BlockConverter {

    public static BlockResponseDTO.BlockResultDTO toBlockResultDTO(Block block) {
        return BlockResponseDTO.BlockResultDTO.builder()
                .id(block.getId())
                .memberId(block.getMember().getId())
                .targetId(block.getTarget().getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static BlockResponseDTO.BlockListDTO blockListDTO(Slice<Block> blockList) {

        List<BlockResponseDTO.BlockResultDTO> blockDTOList = blockList.stream()
                .map(BlockConverter::toBlockResultDTO).collect(Collectors.toList());

        return BlockResponseDTO.BlockListDTO.builder()
                .blockList(blockDTOList)
                .isLast(blockList.isLast())
                .isFirst(blockList.isFirst())
                .listSize(blockList.getSize())
                .hasNext(blockList.hasNext())
                .build();
    }
}
