package com.fastcampus.jober.domain.space.spacewall.spacewallhistory.controller;

import com.fastcampus.jober.domain.space.spacewall.service.SpaceWallService;
import com.fastcampus.jober.domain.space.spacewall.spacewallhistory.dto.SpaceWallHistoryResponse;
import com.fastcampus.jober.domain.space.spacewall.spacewallhistory.service.SpaceWallHistoryService;
import com.fastcampus.jober.global.error.exception.SpaceWallException;
import com.fastcampus.jober.global.error.exception.SpaceWallNotFoundException;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fastcampus.jober.domain.space.spacewall.spacewallhistory.dto.HistoryWrapper.HistoryRequestDTOWrapper;
import static com.fastcampus.jober.domain.space.spacewall.spacewallhistory.dto.HistoryWrapper.HistoryResponseDTOWrapper;
import static com.fastcampus.jober.global.constant.ErrorCode.INVALID_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceWallHistoryController {

    private final SpaceWallHistoryService spaceWallHistoryService;
    private final SpaceWallService spaceWallService;

    /**
     * 공유스페이스 및 컴포넌트 히스토리를 저장합니다.
     * @param request HistoryRequestDTOWrapper
     * @param spaceWallId 공유스페이스id
     * @return 히스토리에 저장된 데이터
     */
    @PostMapping("/history/{spaceWallId}")
    public ResponseEntity<ResponseDTO<HistoryResponseDTOWrapper>> historyAdd(
            @RequestBody HistoryRequestDTOWrapper request,
            @PathVariable(name = "spaceWallId") Long spaceWallId
    ) {
        if (!spaceWallService.checkSpaceWallIdExists(spaceWallId))
            throw new SpaceWallException(INVALID_ID);

        return ResponseEntity
                .ok(new ResponseDTO<>(
                        spaceWallHistoryService.addSpaceWallHistory(request),
                        "히스토리에 저장됩니다."
                ));
    }
    @GetMapping("/history/{memberId}")
    public ResponseEntity<ResponseDTO<List<SpaceWallHistoryResponse.SpaceWallHistoryResponseDTO>>> getRecentHistories(
            @PathVariable Long memberId) {


        List<SpaceWallHistoryResponse.SpaceWallHistoryResponseDTO> histories = spaceWallHistoryService.findRecentHistoryByMemberId(memberId);

        if (histories.isEmpty()) {
            return ResponseEntity.ok(new ResponseDTO<>(histories, "히스토리가 없습니다."));
        }

        return ResponseEntity.ok(new ResponseDTO<>(histories, "히스토리 조회 성공"));
    }

    @GetMapping("/history/{memberId}/{historyId}")
    public ResponseEntity<ResponseDTO<SpaceWallHistoryResponse.SpaceWallHistoryResponseDTO>> getHistory(
            @PathVariable Long memberId,
            @PathVariable Long historyId) {


        if (!spaceWallHistoryService.checkHistoryIdExists(historyId)) {
            throw new SpaceWallNotFoundException("히스토리를 찾을 수 없습니다.");
        }

        SpaceWallHistoryResponse.SpaceWallHistoryResponseDTO history = spaceWallHistoryService.findHistoryByMemberIdAndHistoryId(memberId, historyId);
        return ResponseEntity.ok(new ResponseDTO<>(history, "히스토리 조회 성공"));
    }
}
