package org.example.petwif.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.petwif.domain.common.BaseEntity;
import org.example.petwif.domain.enums.Gender;
import org.example.petwif.domain.enums.Telecom;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Telecom telecom;

    private String address;
    private String status;
    private String email;
    private String pw;

    //LocalDateTime 사용하면 될까요?
    //private LocalDateTime inactiveDate;

    private boolean autoLogin;

    // provider : google이 들어감
    private String provider;

    // providerId : 구굴 로그인 한 유저의 고유 ID가 들어감
    private String providerId;

    // 양방향 매핑을 해야 멤버가 탈퇴할때 pet도 없어지므로 이건 해야함! 그런 관계가 아니면 굳이 안 넣어도 됨 복잡해져서
    @OneToMany(fetch = FetchType.LAZY)
    private List<Pet> myPet = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Block> blockList = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Chat> chatList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ChatRoom> chatRoomList = new ArrayList<>();

}
