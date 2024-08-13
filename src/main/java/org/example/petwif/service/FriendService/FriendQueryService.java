package org.example.petwif.service.FriendService;

import org.example.petwif.domain.entity.Friend;
import org.springframework.data.domain.Slice;

public interface FriendQueryService {

    Slice<Friend> getFriendList(Long memberId, Integer page);
}
