package org.example.petwif.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petwif.domain.common.BaseEntity;
import org.example.petwif.domain.enums.FriendStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="friend_id")
    private Member friend;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    public void setMember(Member member) {
        if (this.member != null)
            member.getFriendList().remove(this);
        this.member = member;
        member.getFriendList().add(this);
    }

    public void setFriend(Member friend) {
        this.friend = friend;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }
}
