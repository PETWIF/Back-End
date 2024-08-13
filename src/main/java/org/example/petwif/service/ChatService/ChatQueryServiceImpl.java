package org.example.petwif.service.ChatService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.domain.entity.Chat;
import org.example.petwif.domain.entity.ChatRoom;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.repository.ChatRepository;
import org.example.petwif.repository.ChatRoomRepository;
import org.example.petwif.repository.MemberRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatQueryServiceImpl implements ChatQueryService{

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    //채팅창 화면 조회
    @Override
    public Optional<Chat> findChat(Long id){
        return chatRepository.findChatById(id);
    }

    @Override
    public Slice<Chat> getChatList(Long chatRoomId, Integer page){
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
        Slice<Chat> ChatRoomPage = chatRepository.findAllByChatRoom(chatRoom, PageRequest.of(page, 10));

        return ChatRoomPage;
    }

    //채팅방 목록 조회
    @Override
    public Optional<ChatRoom> findChatRoom(Long id){
        return chatRoomRepository.findChatRoomById(id);
    }

    @Override
    public Slice<ChatRoom> getChatRoomList(Long memberId, Integer page){
        Member member = memberRepository.findById(memberId).get();
        Slice<ChatRoom> MemberPage = chatRoomRepository.findAllByMember(member, PageRequest.of(page, 10));

        return MemberPage;
    }

    //가장 최근 채팅 조회
    @Override
    public Optional<Chat> getLastChat(Long chatRoomId){
        return chatRepository.findTopByChatRoomIdOrderByCreatedAtDesc(chatRoomId);
    }
}
