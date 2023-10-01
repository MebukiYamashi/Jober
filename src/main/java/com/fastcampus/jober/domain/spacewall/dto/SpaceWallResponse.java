package com.fastcampus.jober.domain.spacewall.dto;

import com.fastcampus.jober.domain.component.dto.ComponentResponse;
import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.spacewall.domain.SpaceWall;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SpaceWallResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDto {

        private Long id;
        private Long createMemberId;
        private String url;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;
        private String pathIds;
        private boolean authorized;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private int sequence;

        private List<ComponentResponse.ComponentResponseDTO> componentList;

        public ResponseDto(SpaceWall spaceWall, List<ComponentResponse.ComponentResponseDTO> componentList) {
            this.id = spaceWall.getId();
            this.createMemberId = spaceWall.getCreateMember().getId();
            this.url = spaceWall.getUrl();
            this.title = spaceWall.getTitle();
            this.description = spaceWall.getDescription();
            this.profileImageUrl = spaceWall.getProfileImageUrl();
            this.backgroundImageUrl = spaceWall.getBackgroundImageUrl();
            this.pathIds = spaceWall.getPathIds();
            this.authorized = spaceWall.isAuthorized();
            this.createdAt = spaceWall.getCreatedAt();
            this.updatedAt = spaceWall.getUpdatedAt();
            this.sequence = spaceWall.getSequence();
            this.componentList = componentList;
        }

        public SpaceWall toEntity() {
            Member member = Member.builder().id(createMemberId).build();

            return SpaceWall.builder()
                    .id(id)
                    .url(url)
                    .title(title)
                    .description(description)
                    .profileImageUrl(profileImageUrl)
                    .backgroundImageUrl(backgroundImageUrl)
                    .pathIds(pathIds)
                    .authorized(authorized)
                    .sequence(sequence)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmptySpaceResponseDto {

        private Long id;
        private Long createMemberId;
        private String url;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;
        private String pathIds;
        private boolean authorized;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private int sequence;
        private List<ComponentResponse.ComponentResponseDTO> componentList;

        public EmptySpaceResponseDto(SpaceWall spaceWall) {
            this.id = spaceWall.getId();
            this.createMemberId = spaceWall.getCreateMember().getId();
            this.url = spaceWall.getUrl();
            this.pathIds = spaceWall.getPathIds();
            this.authorized = spaceWall.isAuthorized();
            this.createdAt = spaceWall.getCreatedAt();
            this.updatedAt = spaceWall.getUpdatedAt();
            this.sequence = spaceWall.getSequence();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SessionDTO {

        private boolean accessable;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UrlUpdateDTO {

        private String updateUrl;
    }
}
