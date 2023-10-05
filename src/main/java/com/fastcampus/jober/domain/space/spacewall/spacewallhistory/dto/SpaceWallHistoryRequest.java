package com.fastcampus.jober.domain.space.spacewall.spacewallhistory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class SpaceWallHistoryRequest {

    @Getter
    @Setter
    public static class SpaceWallHistoryRequestDTO {

        @NotBlank(message = "연관된 공유스페이스 정보가 누락되었습니다.")
        private Long spaceWallId;
        private String url;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;
        @NotBlank(message = "공유스페이스 간 연결 정보(pathIds)가 누락되었습니다.")
        private String pathIds;
        private boolean authorized;
        private int sequence;
        private Long createMemberId;
        private Long parentSpaceWallId;
    }
}