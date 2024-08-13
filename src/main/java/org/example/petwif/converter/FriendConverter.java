package org.example.petwif.converter;

import org.example.petwif.domain.entity.Friend;
import org.example.petwif.web.dto.FriendDTO.FriendResponseDTO;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FriendConverter {

    public static FriendResponseDTO.FriendResultDTO toFriendResultDTO(Friend friend) {
        return FriendResponseDTO.FriendResultDTO.builder()
                .id(friend.getId())
                .memberId(friend.getMember().getId())
                .friendId(friend.getFriend().getId())
                .status(friend.getStatus())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static FriendResponseDTO.FriendListDTO friendListDTO(Slice<Friend> friendList) {

        List<FriendResponseDTO.FriendResultDTO> friendDTOList = friendList.stream()
                .map(FriendConverter::toFriendResultDTO).collect(Collectors.toList());

        return FriendResponseDTO.FriendListDTO.builder()
                .friendList(friendDTOList)
                .isLast(friendList.isLast())
                .isFirst(friendList.isFirst())
                .listSize(friendList.getSize())
                .hasNext(friendList.hasNext())
                .build();
    }
}
