package com.fastcampus.jober.domain.space.spacewall.spacewalltemp.domain;

import com.fastcampus.jober.domain.BaseTimeEntity;
import com.fastcampus.jober.domain.space.spacewall.domain.SpaceWall;
import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.dto.SpaceWallTempRequest.ModifyDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private SpaceWall spaceWall; // 원본의 spaceWAll을 가리킴

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String description;

    @Column(name = "profile_image_url", length = 200)
    private String profileImageUrl;

    @Column(name = "background_image_url", length = 200)
    private String backgroundImageUrl;

    @Column(nullable = false)
    private int sequence;

    public SpaceWallTemp() {

    }

    @Override
    public String toString() {
        return "SpaceWallTemp{" +
            "id=" + id +
            ", spaceWall=" + spaceWall.getId() +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", profileImageUrl='" + profileImageUrl + '\'' +
            ", backgroundImageUrl='" + backgroundImageUrl + '\'' +
            ", sequence=" + sequence +
            '}';
    }

    public void update(ModifyDTO modifyDTO) {
        if (this.title != modifyDTO.getTitle()) {
            this.title = modifyDTO.getTitle();
        }
        if (this.description != modifyDTO.getDescription()) {
            this.description = modifyDTO.getDescription();
        }
        if (this.profileImageUrl != modifyDTO.getProfileImageUrl()) {
            this.profileImageUrl = modifyDTO.getProfileImageUrl();
        }
        if (this.backgroundImageUrl != modifyDTO.getBackgroundImageUrl()) {
            this.backgroundImageUrl = modifyDTO.getProfileImageUrl();
        }
    }
}
