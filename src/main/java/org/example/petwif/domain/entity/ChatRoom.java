package org.example.petwif.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petwif.domain.common.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "other_id")
    private Member other;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_name")
    private Member roomName;

    private String roomStatus;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Chat> chatList = new ArrayList<>();

    //채팅방 생성
    public void setMember(Member member) {
        this.member = member;
        member.getChatRoomList().add(this);
    }

    public void setOther(Member other) {
        this.other = other;
        other.getChatRoomList().add(this);
    }
}
