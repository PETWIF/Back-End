package org.example.petwif.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.petwif.domain.common.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chatRoom_id")
    private ChatRoom chatRoom;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false)
    private boolean isCheck;

    private String imageUrl;

    //채팅 보내기
    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getChatList().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getChatList().add(this);
    }
}
