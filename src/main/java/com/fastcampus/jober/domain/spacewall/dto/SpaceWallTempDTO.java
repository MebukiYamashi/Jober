package com.fastcampus.jober.domain.spacewall.dto;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.spacewall.domain.SpaceWallTemp;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class SpaceWallTempDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TempSaveDTO {
        private Long createMemberId;
        private String url;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;
        private Integer sequence;

        public SpaceWallTemp toEntity() {
            Member member = Member.builder().id(createMemberId).build();
//            Workspace workspace = Workspace.builder().id(workspaceId).build();


            return SpaceWallTemp.builder()
                .url(url)
                .title(title)
                .description(description)
                .profileImageUrl(profileImageUrl)
                .backgroundImageUrl(backgroundImageUrl)
                .sequence(sequence != null ? sequence : 0)
                .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TempResponseDTO {
        private Long id;
        private Long createMemberId;
        private String url;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private int sequence;

        public TempResponseDTO(SpaceWallTemp spaceWall) {
            this.id = spaceWall.getId();
            this.url = spaceWall.getUrl();
            this.title = spaceWall.getTitle();
            this.description = spaceWall.getDescription();
            this.profileImageUrl = spaceWall.getProfileImageUrl();
            this.backgroundImageUrl = spaceWall.getBackgroundImageUrl();
            this.createdAt = spaceWall.getCreatedAt();
            this.updatedAt = spaceWall.getUpdatedAt();
            this.sequence = spaceWall.getSequence();
        }
    }
}
