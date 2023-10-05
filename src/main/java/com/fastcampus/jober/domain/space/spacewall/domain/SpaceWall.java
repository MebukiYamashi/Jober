package com.fastcampus.jober.domain.space.spacewall.domain;

import com.fastcampus.jober.domain.BaseTimeEntity;
import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.space.spacewall.spacewallmember.domain.SpaceWallMember;
import com.fastcampus.jober.domain.space.spacewall.spacewalltemp.domain.SpaceWallTemp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@Builder
@Table(name = "space_wall")
public class SpaceWall extends BaseTimeEntity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "spaceWall")
    @JsonManagedReference
    private List<SpaceWallMember> spaceWallMember;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "create_member_id")
    private Member createMember;

    @Column(name = "parent_space_wall_id")
    private Long parentSpaceWallId;

    @Column(length = 100, unique = true)
    private String url;

    @Column(length = 100)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "profile_image_url", length = 200)
    private String profileImageUrl;

    @Column(name = "background_image_url", length = 200)
    private String backgroundImageUrl;

    @Column(name = "path_ids", length = 100)
    private String pathIds;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean authorized;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int sequence;

    protected SpaceWall() {

    }

    public void update(SpaceWall updatedSpaceWall) {
        if (updatedSpaceWall.getUrl() != null) {
            this.url = updatedSpaceWall.getUrl();
        }
        if (updatedSpaceWall.getTitle() != null) {
            this.title = updatedSpaceWall.getTitle();
        }
        if (updatedSpaceWall.getDescription() != null) {
            this.description = updatedSpaceWall.getDescription();
        }
        if (updatedSpaceWall.getProfileImageUrl() != null) {
            this.profileImageUrl = updatedSpaceWall.getProfileImageUrl();
        }
        if (updatedSpaceWall.getBackgroundImageUrl() != null) {
            this.backgroundImageUrl = updatedSpaceWall.getBackgroundImageUrl();
        }
        if (updatedSpaceWall.getPathIds() != null) {
            this.pathIds = updatedSpaceWall.getPathIds();
        }
        this.authorized = updatedSpaceWall.isAuthorized();
    }

    public void updateBySWT(SpaceWallTemp spaceWallTemp) {
        if (!Objects.equals(this.title, spaceWallTemp.getTitle())) {
            this.title = spaceWallTemp.getTitle();
        }
        if (!Objects.equals(this.description, spaceWallTemp.getDescription())) {
            this.description = spaceWallTemp.getDescription();
        }
        if (!Objects.equals(this.profileImageUrl, spaceWallTemp.getProfileImageUrl())) {
            this.profileImageUrl = spaceWallTemp.getProfileImageUrl();
        }
        if (this.backgroundImageUrl != spaceWallTemp.getBackgroundImageUrl()) {
            this.backgroundImageUrl = spaceWallTemp.getBackgroundImageUrl();
        }
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;

    }

    public void updateUrl(String url) {
        this.url = url;
    }

    public int getSizeOfPathIds() {
        return this.pathIds.length();
    }

    public void updateAuthorized(boolean authorized) {
        this.authorized = authorized;
    }
}
