package org.example.petwif.service.FriendService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.handler.FriendHandler;
import org.example.petwif.domain.entity.Friend;
import org.example.petwif.domain.entity.Member;
import org.example.petwif.domain.enums.FriendStatus;
import org.example.petwif.repository.FriendRepository;
import org.example.petwif.repository.MemberRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendQueryServiceImpl implements FriendQueryService {

    private final MemberRepository memberRepository;

    private final FriendRepository friendRepository;

    @Override
    public Slice<Friend> getFriendList(Long memberId, Integer page) {

        Member member = memberRepository.findById(memberId).get();

        Slice<Friend> friendList = friendRepository.findAllByMemberAndStatusOrderByCreatedAt(member, FriendStatus.ACCEPTED, PageRequest.of(page, 5));

        if (friendList.isEmpty() || page < 0) {
            throw new FriendHandler(ErrorStatus.FRIEND_PAGE_NOT_FOUND);
        }

        return friendList;
    }
}
