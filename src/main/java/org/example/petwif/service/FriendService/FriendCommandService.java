package org.example.petwif.service.FriendService;

import org.example.petwif.domain.entity.Friend;

public interface FriendCommandService {
    Friend friendRequest(Long memberId, Long friendId);
    Friend friendCancel(Long memberId, Long friendId);
    Friend friendAccept(Long memberId, Long friendId);
    Friend friendReject(Long memberId, Long friendId);
    void deleteFriend(Long memberId, Long friendId);
}
