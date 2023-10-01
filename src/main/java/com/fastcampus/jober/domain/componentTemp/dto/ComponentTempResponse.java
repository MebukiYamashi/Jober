package com.fastcampus.jober.domain.componentTemp.dto;

import com.fastcampus.jober.domain.componentTemp.domain.ComponentTemp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ComponentTempResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComponentTempResponseDTO {

        private Long id;
        private Long spaceWallTempId;
        private Long templateId;
        private Long childSpaceWallTempId;
        private Long componentId;
        private String type;
        private boolean visible;
        private String title;
        private String content;
        private int sequence;
        private boolean deleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;


        public static ComponentTempResponseDTO toDTO(ComponentTemp componentTemp) {
            return ComponentTempResponseDTO.builder()
                .id(componentTemp.getId())
                .spaceWallTempId(componentTemp.getSpaceWallTemp().getId())
                .type(componentTemp.getType())
                .componentId(componentTemp.getComponentId())
                .visible(componentTemp.isVisible())
                .title(componentTemp.getTitle())
                .content(componentTemp.getContent())
                .sequence(componentTemp.getSequence())
                .deleted(componentTemp.isDeleted())
                .createdAt(componentTemp.getCreatedAt())
                .updatedAt(componentTemp.getUpdatedAt())
                .build();
        }

        public static ComponentTempResponseDTO toDTOPageType(ComponentTemp componentTemp) {
            return ComponentTempResponseDTO.builder()
                .id(componentTemp.getId())
                .spaceWallTempId(componentTemp.getSpaceWallTemp().getId())
                .childSpaceWallTempId(componentTemp.getChildSpaceWall().getId())
                .type(componentTemp.getType())
                .componentId(componentTemp.getComponentId())
                .visible(componentTemp.isVisible())
                .title(componentTemp.getChildSpaceWall().getTitle())
                .content(componentTemp.getChildSpaceWall().getDescription())
                .sequence(componentTemp.getSequence())
                .deleted(componentTemp.isDeleted())
                .createdAt(componentTemp.getCreatedAt())
                .updatedAt(componentTemp.getUpdatedAt())
                .build();
        }

        public static ComponentTempResponseDTO toDTOTemplateType(ComponentTemp componentTemp) {
            return ComponentTempResponseDTO.builder()
                .id(componentTemp.getId())
                .spaceWallTempId(componentTemp.getSpaceWallTemp().getId())
                .templateId(componentTemp.getTemplate().getId())
                .type(componentTemp.getType())
                .componentId(componentTemp.getComponentId())
                .visible(componentTemp.isVisible())
                .title(componentTemp.getTemplate().getTitle())
                .content(componentTemp.getTemplate().getDescription())
                .sequence(componentTemp.getSequence())
                .deleted(componentTemp.isDeleted())
                .createdAt(componentTemp.getCreatedAt())
                .updatedAt(componentTemp.getUpdatedAt())
                .build();
        }
    }






}
