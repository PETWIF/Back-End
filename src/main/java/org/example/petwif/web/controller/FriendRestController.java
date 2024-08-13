package org.example.petwif.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.ApiResponse;
import org.example.petwif.converter.FriendConverter;
import org.example.petwif.domain.entity.Friend;
import org.example.petwif.service.FriendService.FriendCommandService;
import org.example.petwif.service.FriendService.FriendQueryService;
import org.example.petwif.validation.annotation.ExistMember;
import org.example.petwif.web.dto.FriendDTO.FriendResponseDTO;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class FriendRestController {

    private final FriendCommandService friendCommandService;
    private final FriendQueryService friendQueryService;

    @PostMapping("/{memberId}/friends/{friendId}/requests")
    @Operation(summary = "친구 요청 API", description = "특정 멤버가 다른 멤버에게 친구 요청하는 API입니다.")
    @Parameters({
            @Parameter(name = "memberId", description = "친구 요청을 하는 멤버의 아이디, path variable 입니다!"),
            @Parameter(name = "friendId", description = "친구 요청 당하는 멤버의 아이디, path variable 입니다!")
    })
    public ApiResponse<FriendResponseDTO.FriendResultDTO> friendRequest(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                        @ExistMember @PathVariable(name = "friendId") Long friendId) {
        Friend friend = friendCommandService.friendRequest(memberId, friendId);
        return ApiResponse.onSuccess(FriendConverter.toFriendResultDTO(friend));
    }

    @PutMapping("/{memberId}/friend/{friendId}/cancellations")
    @Operation(summary = "친구 요청 취소 API", description = "특정 멤버가 다른 멤버에게 한 친구 요청을 취소하는 API입니다.")
    @Parameters({
            @Parameter(name = "memberId", description = "친구 요청을 취소하는 멤버의 아이디, path variable 입니다!"),
            @Parameter(name = "friendId", description = "친구 요청 취소 당하는 멤버의 아이디, path variable 입니다!")
    })
    public ApiResponse<FriendResponseDTO.FriendResultDTO> friendCancel(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                       @ExistMember @PathVariable(name = "friendId") Long friendId) {
        Friend friend = friendCommandService.friendCancel(memberId, friendId);
        return ApiResponse.onSuccess(FriendConverter.toFriendResultDTO(friend));
    }

    @PutMapping("/{memberId}/friend/{friendId}/acceptances")
    @Operation(summary = "친구 요청 수락 API", description = "다른 멤버에게 온 친구 요청에 대해 수락하는 API입니다.")
    @Parameters({
            @Parameter(name = "memberId", description = "친구 요청을 수락하는 멤버의 아이디, path variable 입니다!"),
            @Parameter(name = "friendId", description = "친구 요청을 했던 멤버의 아이디, path variable 입니다!")
    })
    public ApiResponse<FriendResponseDTO.FriendResultDTO> friendAccept(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                       @ExistMember @PathVariable(name = "friendId") Long friendId) {
        Friend friend = friendCommandService.friendAccept(memberId, friendId);
        return ApiResponse.onSuccess(FriendConverter.toFriendResultDTO(friend));
    }

    @PutMapping("/{memberId}/friend/{friendId}/rejections")
    @Operation(summary = "친구 요청 거절 API", description = "다른 멤버에게 온 친구 요청에 대해 거절하는 API입니다.")
    @Parameters({
            @Parameter(name = "memberId", description = "친구 요청을 거절하는 멤버의 아이디, path variable 입니다!"),
            @Parameter(name = "friendId", description = "친구 요청을 했던 멤버의 아이디, path variable 입니다!")
    })
    public ApiResponse<FriendResponseDTO.FriendResultDTO> friendReject(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                       @ExistMember @PathVariable(name = "friendId") Long friendId) {
        Friend friend = friendCommandService.friendReject(memberId, friendId);
        return ApiResponse.onSuccess(FriendConverter.toFriendResultDTO(friend));
    }

    @GetMapping("/{memberId}/friends")
    @Operation(summary = "특정 멤버의 친구 목록 조회 API", description = "특정 멤버의 친구 목록을 조회하는 API이며, 페이징을 포함합니다. query String으로 page 번호를 주세요.")
    @Parameters({
            @Parameter(name = "memberId", description = "멤버의 아이디, path variable 입니다!")
    })
    public ApiResponse<FriendResponseDTO.FriendListDTO> getFriendList(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                      @RequestParam(name = "page", defaultValue = "0") Integer page) {
        Slice<Friend> friendList = friendQueryService.getFriendList(memberId, page);
        return ApiResponse.onSuccess(FriendConverter.friendListDTO(friendList));
    }

    @DeleteMapping("/{memberId}/friends/{friendId}")
    @Operation(summary = "친구 삭제 API", description = "친구를 삭제하는 API입니다.")
    @Parameters({
            @Parameter(name = "memberId", description = "친구를 삭제하는 멤버의 아이디, path variable 입니다!"),
            @Parameter(name = "friendId", description = "삭제당한 멤버의 아이디, path variable 입니다!")
    })
    public ApiResponse<FriendResponseDTO.FriendResultDTO> deleteFriend(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                       @ExistMember @PathVariable(name = "friendId") Long friendId) {
        friendCommandService.deleteFriend(memberId, friendId);
        return ApiResponse.onSuccess(null);
    }
}
