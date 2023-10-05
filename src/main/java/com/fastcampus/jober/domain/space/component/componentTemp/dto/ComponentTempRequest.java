package com.fastcampus.jober.domain.space.component.componentTemp.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ComponentTempRequest {
    
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComponentTempRequestDTO{
        private Long id;
        private Long spaceWallTempId;
        private Long templateId;
        private Long childSpaceWallId;
        private Long componentId;
        private String type;
        private boolean visible;
        private String title;
        private String content;
        private int sequence;
        private boolean deleted;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddDTO{
        private Long spaceWallId;
        private String type;
        private int sequence;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyDTO{
        private Long componentTempId;
        private Long templateId;
        private Long thisSpaceWallId;
        private String title;
        private String content;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyDTOInSWT{
        private Long componentTempId;
        private boolean visible;
        private int sequence;
        private boolean deleted;
    }

}
