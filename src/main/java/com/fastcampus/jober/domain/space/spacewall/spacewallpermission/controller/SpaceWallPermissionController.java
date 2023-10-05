package com.fastcampus.jober.domain.space.spacewall.spacewallpermission.controller;

import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.dto.SpaceWallPermissionRequest;
import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.dto.SpaceWallPermissionResponse;
import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.service.SpaceWallPermissionService;
import com.fastcampus.jober.global.security.auth.session.MemberDetails;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spaces")
@RequiredArgsConstructor
public class SpaceWallPermissionController {

    private final SpaceWallPermissionService spaceWallPermissionService;

    @PutMapping("authority/{id}")
    public ResponseEntity<ResponseDTO<SpaceWallPermissionResponse>> updatePermission(
            @PathVariable Long id,
            @RequestBody SpaceWallPermissionRequest requestDto,
            @AuthenticationPrincipal MemberDetails memberDetails) {

        SpaceWallPermissionResponse response = spaceWallPermissionService.updatePermission(id, requestDto, memberDetails);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "권한을 수정하였습니다.", response));
    }

    @PutMapping("move/{id}")
    public ResponseEntity<ResponseDTO<SpaceWallPermissionResponse>> moveSpaceWallPermission(
            @PathVariable Long id,
            @RequestBody SpaceWallPermissionRequest requestDto,
            @AuthenticationPrincipal MemberDetails memberDetails) {

        SpaceWallPermissionResponse response = spaceWallPermissionService.moveSpaceWallPermission(id, requestDto.getTargetSequence(), memberDetails);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "공유페이지의 위치를 수정하였습니다.", response));
    }
}
