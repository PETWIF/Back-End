package org.example.petwif.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.petwif.domain.common.BaseEntity;
import org.example.petwif.domain.enums.Scope;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album extends BaseEntity {

    @Id //앨범 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //사용자 FK
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL) // 일대일 연관관계, 표지 1개의 사진
    @JoinColumn(name = "cover_image_id")
    private AlbumImage coverImage;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL) //양방향 연관관계, 표지 제외 나머지 앨범 내 사진 리스트
    @Builder.Default
    private List<AlbumImage> albumImages = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    @Builder.Default
    private List<AlbumLike> albumLikes = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    @Builder.Default
    private List<AlbumBookmark> albumBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Comment> commentList;

   // @Column(nullable = false)
    private String title;

   // @Column(nullable = false)
    private String content;

   // @Column(columnDefinition = "integer default 0")
    private Integer view;

    @Enumerated(EnumType.STRING) //공개 범위
    private Scope scope;

    public void update(String title, String content, Scope scope){
        this.title = title;
        this.content = content;
        this.scope = scope;
    }

    public void incrementViewCount() {
        this.view++;
    }
}
