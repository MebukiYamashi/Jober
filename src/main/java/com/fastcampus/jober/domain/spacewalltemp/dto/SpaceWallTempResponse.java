package com.fastcampus.jober.domain.spacewalltemp.dto;

import com.fastcampus.jober.domain.componentTemp.dto.ComponentTempResponse.ComponentTempResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SpaceWallTempResponse {


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceWallTempResponseDTO{
        private Long spaceWallTempId;
        private Long spaceWallId;
        private String title;
        private String description;
        private String profileImageUrl;
        private String backgroundImageUrl;
        private int sequence;
        private LocalDateTime createdAt;
        private LocalDateTime updateddAt;

        private List<ComponentTempResponseDTO> componentTempList;
    }

}
