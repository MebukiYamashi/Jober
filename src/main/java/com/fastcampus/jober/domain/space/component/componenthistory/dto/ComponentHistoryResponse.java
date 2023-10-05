package com.fastcampus.jober.domain.space.component.componenthistory.dto;

import com.fastcampus.jober.domain.space.component.componenthistory.domain.ComponentHistory;
import com.fastcampus.jober.global.constant.Type;
import lombok.Getter;
import lombok.Setter;

public class ComponentHistoryResponse {

    @Getter
    @Setter
    public static class ComponentHistoryResponseDTO {

        private Long id;
        private Long templateId;
        private Long spaceWallHistoryId;
        private Long thisSpaceWallId;
        private Type type;
        private Long parentSpaceWallId;
        private boolean visible;
        private String title;
        private String content;
        private int sequence;

        public ComponentHistoryResponseDTO(ComponentHistory componentHistory) {
            this.id = componentHistory.getId();
            this.templateId = componentHistory.getTemplateId();
            this.spaceWallHistoryId = componentHistory.getSpaceWallHistoryId();
            this.thisSpaceWallId = componentHistory.getThisSpaceWallId();
            this.type = componentHistory.getType();
            this.parentSpaceWallId = componentHistory.getParentSpaceWallId();
            this.visible = componentHistory.isVisible();
            this.title = componentHistory.getTitle();
            this.content = componentHistory.getContent();
            this.sequence = componentHistory.getSequence();
        }
    }
}
