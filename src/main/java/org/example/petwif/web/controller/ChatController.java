package org.example.petwif.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.ApiResponse;
import org.example.petwif.converter.ChatConverter;
import org.example.petwif.domain.entity.Chat;
import org.example.petwif.domain.entity.ChatRoom;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.repository.ChatRepository;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.service.ChatService.ChatCommandService;
import org.example.petwif.service.ChatService.ChatQueryService;

import org.example.petwif.validation.annotation.ExistChatRoom;
import org.example.petwif.validation.annotation.ExistMember;
import org.example.petwif.web.dto.ChatDTO.ChatRequestDTO;
import org.example.petwif.web.dto.ChatDTO.ChatResponseDTO;
import org.springframework.data.domain.Slice;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Controller
@RequiredArgsConstructor
@Validated
@RequestMapping("/chats")
public class ChatController {

    private final ChatCommandService chatCommandService;
    private final ChatQueryService chatQueryService;
    private final MemberRepository memberRepository;
    private final ChatRepository  chatRepository;

    //채팅 생성 - 완
    @PostMapping("/{memberId}")
    @Operation(summary = "채팅방 생성 API")
    public ApiResponse<ChatResponseDTO.CreateChatRoomResultDTO> createChatRoom(@ExistMember @PathVariable(name = "memberId") Long memberId,
                                                                               @ExistMember @RequestParam(name = "otherId") Long otherId) {
        ChatRoom chatRoom = chatCommandService.createChatRoom(memberId, otherId);
        return ApiResponse.onSuccess(ChatConverter.createChatRoomResultDTO(chatRoom));
    }

    //채팅 보내기 - 완? (WebSocket으로 수정 필요)
    @PostMapping("/{chatRoomId}/send")
    @Operation(summary = "채팅 메시지 전송 API")
    public ApiResponse<ChatResponseDTO.SendChatResultDTO> sendChat(@RequestBody @Valid ChatRequestDTO.SendChatDTO request,
                                                                   @ExistMember @RequestParam(name = "memberId") Long memberId,
                                                                   @ExistChatRoom @PathVariable(name = "chatRoomId") Long chatRoomId){
        Chat chat = chatCommandService.sendChat(memberId, chatRoomId, request);
        return ApiResponse.onSuccess(ChatConverter.sendChatResultDTO(chat));
    }

    //채팅 전송 - WebSocket (실시간 채팅 되긴 하는데 DB에 저장이 안됨)
    @MessageMapping("/{chatRoomId}/sending")
    @SendTo("/topic/public")
    @Operation(summary = "채팅 메시지 전송 API - WebSocket")
    public Chat sendingChat(Chat chat){
        Member member = memberRepository.findById(chat.getMember().getId()).get();
        chatRepository.save(chat);

        return chat;
    }

    //채팅창 화면 조회 - 완
    @GetMapping("/{chatRoomId}")
    @Operation(summary = "채팅창 화면 조회 API" , description = "채팅창 화면 (채팅방의 대화 내용) 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "chatRoomId", description = "채팅방 아이디, path variable 입니다!")
    })
    public ApiResponse<ChatResponseDTO.ChatPreviewListDTO> getChatList(@ExistChatRoom @PathVariable(name = "chatRoomId") Long chatRoomId,
                                                                       @RequestParam(name = "page")Integer page){
        Slice<Chat> chatList = chatQueryService.getChatList(chatRoomId, page);
        return ApiResponse.onSuccess(ChatConverter.toChatPreviewListDTO(chatList));
    }

    //채팅방 목록 조회 - 완..? (member가 아닌 other일 경우 채팅방 목록이 안뜸........)
    @GetMapping("/")
    @Operation(summary = "채팅방 목록 조회 API" , description = "전체 채팅방 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId")
    })
    public ApiResponse<ChatResponseDTO.ChatRoomPreviewListDTO> getChatRoomList(@ExistMember @RequestParam(name = "memberId") Long memberId,
                                                                               @RequestParam(name = "page") Integer page){
        Slice<ChatRoom> chatRoomList = chatQueryService.getChatRoomList(memberId, page);
        return ApiResponse.onSuccess(ChatConverter.toChatRoomPreviewListDTO(chatRoomList));
    }

    //가장 최근 채팅 조회 - 완
    @GetMapping("/last-chat/{chatRoomId}")
    @Operation(summary = "가장 최근 채팅 조회 API" , description = "가장 최근 전송된 채팅을 조회하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "chatRoomId")
    })
    public ApiResponse<ChatResponseDTO.LastChatPreviewDTO> getLastChat(@ExistChatRoom @RequestParam(name = "chatRoomId") Long chatRoomId){
        Optional<Chat> lastChat = chatQueryService.getLastChat(chatRoomId);
        return ApiResponse.onSuccess(ChatConverter.toLastChatPreviewDTO(lastChat.get()));
    }

    //채팅방 나가기 - 완..? (other로 참여했을 경우 채팅방에서 나가지 못함, member가 나갈 경우 채팅방 자체가 사라짐)
    @DeleteMapping("/{chatRoomId}")
    @Operation(summary = "채팅방 나가기 API")
    public ApiResponse<Void> deleteChat(@RequestParam Long memberId, @PathVariable(name = "chatRoomId") Long chatRoomId) {
        chatCommandService.deleteChat(memberId, chatRoomId);
        return ApiResponse.onSuccess(null);
    }
}
