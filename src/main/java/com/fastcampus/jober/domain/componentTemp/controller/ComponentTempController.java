package com.fastcampus.jober.domain.componentTemp.controller;

import com.fastcampus.jober.domain.componentTemp.dto.ComponentTempRequest;
import com.fastcampus.jober.domain.componentTemp.dto.ComponentTempResponse;
import com.fastcampus.jober.domain.componentTemp.service.ComponentTempService;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/componentTemps")
public class ComponentTempController {

    private final ComponentTempService componentTempService;

    /**
     * 임시 컴포넌트의 생성
     * todo 예외처리, 트랜잭션 관리
     * @param addDTO
     * @return ComponentTempResponseDTO
     */
    @PostMapping("/new")
    public ResponseEntity<ResponseDTO<ComponentTempResponse.ComponentTempResponseDTO>> componentTempAdd(
        @RequestBody ComponentTempRequest.ComponentTempRequestDTO addDTO) {

        ComponentTempResponse.ComponentTempResponseDTO componentTempResponseDTO = componentTempService.addComponentTemp(
            addDTO);

        return new ResponseEntity<>(
            new ResponseDTO<>(HttpStatus.CREATED, "임시 컴포넌트가 추가 되었습니다.", componentTempResponseDTO),
            HttpStatus.CREATED
        );
    }

}