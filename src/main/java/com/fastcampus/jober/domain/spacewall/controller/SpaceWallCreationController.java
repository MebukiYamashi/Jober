package com.fastcampus.jober.domain.spacewall.controller;

import com.fastcampus.jober.domain.spacewall.dto.SpaceWallRequest;
import com.fastcampus.jober.domain.spacewall.dto.SpaceWallResponse;
import com.fastcampus.jober.domain.spacewall.service.SpaceWallService;
import com.fastcampus.jober.domain.spacewallpermission.service.SpaceWallPermissionService;
import com.fastcampus.jober.global.auth.session.MemberDetails;
import com.fastcampus.jober.global.constant.Auths;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newspaces")
@RequiredArgsConstructor
public class SpaceWallCreationController {

    private final SpaceWallService spaceWallService;
    private final SpaceWallPermissionService permissionService;

    @PostMapping
    public ResponseEntity<ResponseDTO<SpaceWallResponse.ResponseDto>> create(
            @RequestBody SpaceWallRequest.CreateDto createDto,
            @AuthenticationPrincipal MemberDetails memberDetails) {

        SpaceWallResponse.ResponseDto createdSpaceWallDto = spaceWallService.create(createDto, memberDetails);

        if (createdSpaceWallDto == null) {
            return ResponseEntity.badRequest().body(null);
        }

        permissionService.assignPermissionToSpaceWall(Auths.OWNER, createdSpaceWallDto.getId(), memberDetails.getMember().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(HttpStatus.CREATED, "공유페이지가 생성되었습니다.", createdSpaceWallDto));
    }

    @PostMapping("/empty")
    public ResponseEntity<ResponseDTO<SpaceWallResponse.EmptySpaceResponseDto>> createEmptySpace(
            @RequestBody SpaceWallRequest.CreateDto createDto,
            @AuthenticationPrincipal MemberDetails memberDetails) {

        SpaceWallResponse.EmptySpaceResponseDto createdEmptySpaceDto = spaceWallService.createEmptySpace(createDto, memberDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(HttpStatus.CREATED, "빈 공유페이지가 생성되었습니다.", createdEmptySpaceDto));
    }
}
