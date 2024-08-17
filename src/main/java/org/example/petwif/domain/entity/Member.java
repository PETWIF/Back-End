package org.example.petwif.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.petwif.domain.common.BaseEntity;
import org.example.petwif.domain.enums.Gender;
import org.example.petwif.domain.enums.Telecom;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profile_url;

    private String name;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String oauthProvider;

    //private String accessToken;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Telecom telecom;

    private String address;

    private String status;
    @Email
    private String email;
    @Size(min = 12)
    private String pw;
    //LocalDateTime 사용하면 될까요?
    //private LocalDateTime inactiveDate;

    private boolean autoLogin;

    private Boolean friendRequestNoti = true;

    private Boolean friendAcceptNoti = true;

    private Boolean likeNoti = true;

    private Boolean bookmarkNoti = true;

    private Boolean commentNoti = true;

    // 양방향 매핑을 해야 멤버가 탈퇴할때 pet도 없어지므로 이건 해야함! 그런 관계가 아니면 굳이 안 넣어도 됨 복잡해져서
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private List<Pet> myPet = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Block> blockList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Friend> friendList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Chat> chatList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ChatRoom> chatRoomList = new ArrayList<>();

    @Builder
    public Member(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
