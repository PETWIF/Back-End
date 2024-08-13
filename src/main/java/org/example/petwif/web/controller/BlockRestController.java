package org.example.petwif.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.ApiResponse;
import org.example.petwif.converter.BlockConverter;
import org.example.petwif.domain.entity.Block;
import org.example.petwif.service.BlockService.BlockCommandService;
import org.example.petwif.service.BlockService.BlockQueryService;
import org.example.petwif.validation.annotation.ExistMember;
import org.example.petwif.web.dto.BlockDTO.BlockResponseDTO;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class BlockRestController {

    private final BlockCommandService blockCommandService;;
    private final BlockQueryService blockQueryService;

    @PostMapping("/{memberId}/blocks/{targetId}")
    @Operation(summary = "멤버 차단 API", description = "특정 멤버가 다른 멤버를 차단하는 API입니다.")
    @Parameters({
            @Parameter(name = "memberId", description = "차단을 하는 멤버의 아이디, path variable 입니다!"),
            @Parameter(name = "targetId", description = "차단을 당하는 멤버의 아이디, path variable 입니다!")
    })
    public ApiResponse<BlockResponseDTO.BlockResultDTO> blockMember(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                    @ExistMember @PathVariable(name = "targetId") Long targetId) {
        Block block = blockCommandService.blockMember(memberId, targetId);
        return ApiResponse.onSuccess(BlockConverter.toBlockResultDTO(block));
    }

    @GetMapping("/{memberId}/blocks")
    @Operation(summary = "특정 멤버의 차단 목록 조회 API", description = "특정 멤버의 차단 목록을 조회하는 API이며, 페이징을 포함합니다. query String으로 page 번호를 주세요.")
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "현재 페이지, query parameter 입니다!")
    })
    public ApiResponse<BlockResponseDTO.BlockListDTO> getBlockList(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                   @RequestParam(name = "page" ,defaultValue = "0") Integer page) {
        Slice<Block> blockList = blockQueryService.getBlockList(memberId, page);
        return ApiResponse.onSuccess(BlockConverter.blockListDTO(blockList));
    }

    @DeleteMapping("/{memberId}/blocks/{targetId}")
    @Operation(summary = "멤버 차단 해제 API", description = "특정 멤버가 다른 멤버에 대한 차단을 해제하는 API입니다.")
    @Parameters({
            @Parameter(name = "memberId", description = "차단 해제를 하는 멤버의 아이디, path variable 입니다!"),
            @Parameter(name = "targetId", description = "차단 해제를 당하는 멤버의 아이디, path variable 입니다!")
    })
    public ApiResponse<Void> unblockMember(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                           @ExistMember @PathVariable(name = "targetId") Long targetId) {
        blockCommandService.unblockMember(memberId, targetId);
        return ApiResponse.onSuccess(null);
    }
}
