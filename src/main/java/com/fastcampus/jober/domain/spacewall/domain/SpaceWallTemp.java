package com.fastcampus.jober.domain.spacewall.domain;

import com.fastcampus.jober.domain.BaseTimeEntity;
import com.fastcampus.jober.domain.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Builder
@Table(name = "space_wall_temp")
public class SpaceWallTemp extends BaseTimeEntity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "space_wall_id")
    private SpaceWall spaceWall;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "create_member_id")
    private Member createMember;

    @Column(length = 100)
    private String url;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String description;

    @Column(name = "profile_image_url", length = 200)
    private String profileImageUrl;

    @Column(name = "background_image_url", length = 200)
    private String backgroundImageUrl;

    @Column(name = "path_ids", length = 100)
    private String pathIds;

    @Column(name = "share_url", length = 100)
    private String shareUrl;

    @Column(name = "share_expired_at")
    private LocalDateTime shareExpiredAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private int sequence;

    protected SpaceWallTemp() {

    }
}
