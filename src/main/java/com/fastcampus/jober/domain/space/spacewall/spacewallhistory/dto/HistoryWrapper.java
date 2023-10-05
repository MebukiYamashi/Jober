package com.fastcampus.jober.domain.space.spacewall.spacewallhistory.dto;

import com.fastcampus.jober.domain.space.component.componenthistory.dto.ComponentHistoryRequest;
import com.fastcampus.jober.domain.space.component.componenthistory.dto.ComponentHistoryResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class HistoryWrapper {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryRequestDTOWrapper {

        @Valid SpaceWallHistoryRequest.SpaceWallHistoryRequestDTO spaceWallHistoryRequestDTO;
        @Valid List<ComponentHistoryRequest.ComponentHistoryRequestDTO> componentHistoryRequestDTOs;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryResponseDTOWrapper {

        SpaceWallHistoryResponse.SpaceWallHistoryResponseDTO spaceWallHistoryResponseDTO;
        List<ComponentHistoryResponse.ComponentHistoryResponseDTO> componentHistoryResponseDTOs;
    }
}
