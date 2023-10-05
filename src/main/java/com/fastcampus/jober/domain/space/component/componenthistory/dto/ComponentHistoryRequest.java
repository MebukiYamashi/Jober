package com.fastcampus.jober.domain.space.component.componenthistory.dto;

import com.fastcampus.jober.global.constant.Type;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class ComponentHistoryRequest {

    @Getter
    @Setter
    public static class ComponentHistoryRequestDTO {

        @NotBlank(message = "템플릿 정보가 누락되었습니다.")
        private Long templateId;
        @NotBlank(message = "연관된 공유스페이스 히스토리 정보가 누락되었습니다.")
        private Long spaceWallHistoryId;
        private Long thisSpaceWallId;
        @NotBlank(message = "컴포넌트 분류 정보(type)가 누락되었습니다.")
        private Type type;
        private boolean visible;
        private String title;
        private String content;
        private int sequence;
        @NotBlank(message = "연관된 공유스페이스 정보가 누락되었습니다.")
        private Long parentSpaceWallId;
    }
}
