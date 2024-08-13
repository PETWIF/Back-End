package org.example.petwif.service.FriendService;

import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.apiPayload.exception.handler.FriendHandler;
import org.example.petwif.domain.entity.Friend;
import org.example.petwif.domain.enums.FriendStatus;
import org.example.petwif.repository.FriendRepository;
import org.example.petwif.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendCommandServiceImpl implements FriendCommandService {

    private final MemberRepository memberRepository;

    private final FriendRepository friendRepository;

    @Override
    @Transactional
    public Friend friendRequest(Long memberId, Long friendId) {

        Friend me = friendRepository.findByMember_IdAndFriend_Id(memberId, friendId).orElse(new Friend());

        if (me.getStatus() == FriendStatus.PENDING) {
            throw new FriendHandler(ErrorStatus.FRIEND_REQUEST_ALREADY_EXIST);
        }

        if (me.getStatus() == FriendStatus.ACCEPTED) {
            throw new FriendHandler(ErrorStatus.FRIEND_ALREADY_EXIST);
        }

        me.setMember(memberRepository.findById(memberId).get());
        me.setFriend(memberRepository.findById(friendId).get());
        me.setStatus(FriendStatus.PENDING);

        return friendRepository.save(me);
    }

    @Override
    @Transactional
    public Friend friendCancel(Long memberId, Long friendId) {

        Friend me = friendRepository.findByMember_IdAndFriend_Id(memberId, friendId).orElseThrow(() -> new FriendHandler(ErrorStatus.FRIEND_REQUEST_NOT_FOUND));

        if (me.getStatus() != FriendStatus.PENDING) {
            throw new FriendHandler(ErrorStatus.FRIEND_REQUEST_NOT_FOUND);
        }

        me.setStatus(FriendStatus.CANCELLED);

        return friendRepository.save(me);
    }

    @Override
    @Transactional
    public Friend friendAccept(Long memberId, Long friendId) {

        Friend friend = friendRepository.findByMember_IdAndFriend_Id(friendId, memberId).orElseThrow(() -> new FriendHandler(ErrorStatus.FRIEND_REQUEST_NOT_FOUND));

        if (friend.getStatus() != FriendStatus.PENDING) {
            throw new FriendHandler(ErrorStatus.FRIEND_REQUEST_NOT_FOUND);
        }

        friend.setStatus(FriendStatus.ACCEPTED);

        friendRepository.save(friend);

        Friend me = friendRepository.findByMember_IdAndFriend_Id(memberId, friendId).orElse(new Friend());

        me.setMember(memberRepository.findById(memberId).get());
        me.setFriend(memberRepository.findById(friendId).get());
        me.setStatus(FriendStatus.ACCEPTED);

        return friendRepository.save(me);
    }

    @Override
    @Transactional
    public Friend friendReject(Long memberId, Long friendId) {

        Friend friend = friendRepository.findByMember_IdAndFriend_Id(friendId, memberId).orElseThrow(() -> new FriendHandler(ErrorStatus.FRIEND_REQUEST_NOT_FOUND));

        if (friend.getStatus() != FriendStatus.PENDING) {
            throw new FriendHandler(ErrorStatus.FRIEND_REQUEST_NOT_FOUND);
        }

        friend.setStatus(FriendStatus.REJECTED);

        friendRepository.save(friend);

        Friend me = friendRepository.findByMember_IdAndFriend_Id(memberId, friendId).orElse(new Friend());

        me.setMember(memberRepository.findById(memberId).get());
        me.setFriend(memberRepository.findById(friendId).get());
        me.setStatus(FriendStatus.REJECTED);

        return friendRepository.save(me);
    }

    @Override
    @Transactional
    public void deleteFriend(Long memberId, Long friendId) {

        Friend friend = friendRepository.findByMember_IdAndFriend_Id(friendId, memberId).orElseThrow(() -> new FriendHandler(ErrorStatus.FRIEND_NOT_FOUND));
        Friend me = friendRepository.findByMember_IdAndFriend_Id(memberId, friendId).orElseThrow(() -> new FriendHandler(ErrorStatus.FRIEND_NOT_FOUND));

        if (friend.getStatus() != FriendStatus.ACCEPTED && me.getStatus() != FriendStatus.ACCEPTED) {
            throw new FriendHandler(ErrorStatus.FRIEND_NOT_FOUND);
        }

        friendRepository.delete(friend);
        friendRepository.delete(me);
    }
}
