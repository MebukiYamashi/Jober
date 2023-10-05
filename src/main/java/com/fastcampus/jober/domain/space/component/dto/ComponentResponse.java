package com.fastcampus.jober.domain.space.component.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ComponentResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComponentResponseDTO{
        private Long id;
        private Long spaceWallId;
        private Long templateId;
        private Long childSpaceWallId;
        private String type;
        private boolean visible;
        private String title;
        private String content;
        private int sequence;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

    }

}
