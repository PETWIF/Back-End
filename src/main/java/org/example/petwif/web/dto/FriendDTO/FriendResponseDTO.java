package org.example.petwif.web.dto.FriendDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petwif.domain.enums.FriendStatus;

import java.time.LocalDateTime;
import java.util.List;

public class FriendResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendResultDTO {
        Long id;
        Long memberId;
        Long friendId;
        FriendStatus status;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendListDTO {
        List<FriendResultDTO> friendList;
        Integer listSize;
        Boolean isFirst;
        Boolean isLast;
        Boolean hasNext;
    }
}
