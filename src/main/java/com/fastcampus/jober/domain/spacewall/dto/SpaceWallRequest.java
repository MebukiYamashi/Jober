package com.fastcampus.jober.domain.spacewall.dto;

import com.fastcampus.jober.domain.member.domain.Member;
import com.fastcampus.jober.domain.spacewall.domain.SpaceWall;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SpaceWallRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateDto {

        private String url;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;
        private String pathIds;
        private boolean authorized;
        private Integer sequence;

        public SpaceWall toEntityWithMember(Member member) {
            return SpaceWall.builder()
                .createMember(member)
                .url(url)
                .title(title)
                .description(description)
                .profileImageUrl(profileImageUrl)
                .backgroundImageUrl(backgroundImageUrl)
                .pathIds(pathIds)
                .authorized(authorized)
                .sequence(sequence != null ? sequence : 0)
                .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateDto {

        private String url;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;
        private String pathIds;
        private boolean authorized;

        public SpaceWall toEntity() {
            return SpaceWall.builder()
                .url(url)
                .title(title)
                .description(description)
                .profileImageUrl(profileImageUrl)
                .backgroundImageUrl(backgroundImageUrl)
                .pathIds(pathIds)
                .authorized(authorized)
                .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UrlUpdateDto {

        @NotBlank(message = "URL은 빈 값이거나 공백이 들어갈 수 없습니다.")
        private String updateUrl;
    }
    @Getter
    @NoArgsConstructor
    public static class EmptySpaceCreateDto {

        private String url;
        private String pathIds;
        private Integer sequence;

        public SpaceWall toEntityWithMember(Member member) {
            return SpaceWall.builder()
                    .createMember(member)
                    .url(url)
                    .pathIds(pathIds)
                    .sequence(sequence != null ? sequence : 0)
                    .title(null)
                    .description(null)
                    .profileImageUrl(null)
                    .backgroundImageUrl(null)
                    .authorized(false)
                    .build();
        }
    }

}