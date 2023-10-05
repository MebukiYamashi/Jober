package com.fastcampus.jober.domain.space.spacewall.spacewalltemp.dto;

import com.fastcampus.jober.domain.space.component.componentTemp.dto.ComponentTempRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SpaceWallTempRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddDTO{
        private Long spaceWallId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyDTO{
        private Long spaceWallTempId;
        private Long spaceWallId;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;

        private List<ComponentTempRequest.ModifyDTOInSWT> componentTempList;
    }




}
