package org.example.petwif.service.ChatService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.GeneralException;
import org.example.petwif.converter.ChatConverter;
import org.example.petwif.domain.entity.Chat;
import org.example.petwif.domain.entity.ChatRoom;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.repository.ChatRepository;
import org.example.petwif.repository.ChatRoomRepository;
import org.example.petwif.repository.MemberChatRoomRepository;
import org.example.petwif.repository.MemberRepository;
import org.example.petwif.web.dto.ChatDTO.ChatRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatCommandServiceImpl implements ChatCommandService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;
    //private final AmazonS3Manager s3Manager; 사진 전송

    @Override
    @Transactional
    public ChatRoom createChatRoom(Long memberId, Long otherId) { //채팅 생성
        ChatRoom chatRoom = new ChatRoom();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        Member other = memberRepository.findById(otherId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        chatRoom.setMember(member);
        chatRoom.setOther(other);

        return chatRoomRepository.save(chatRoom);
    }

    @Override
    @Transactional
    public Chat sendChat(Long memberId, Long chatRoomId, ChatRequestDTO.SendChatDTO request) { //채팅 보내기
        Chat chat = ChatConverter.toChat(request);

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHATROOM_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        if (!chatRoom.getMember().equals(member) && !chatRoom.getOther().equals(member)) { //채팅방에 없는 사용자가 채팅 메시지를 보내려고 할 경우
            throw new IllegalArgumentException("Invalid chat room");
        }

        chat.setChatRoom(chatRoom);
        chat.setMember(member);

        return chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void deleteChat(Long memberId, Long chatRoomId) { //채팅방 나가기
        ChatRoom chatRoom = chatRoomRepository.findAllByMemberIdAndId(memberId, chatRoomId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHATROOM_NOT_FOUND));

        chatRoomRepository.delete(chatRoom);
    }
}

