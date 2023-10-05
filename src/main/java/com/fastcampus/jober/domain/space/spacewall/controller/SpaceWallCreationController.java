package com.fastcampus.jober.domain.space.spacewall.controller;

import com.fastcampus.jober.domain.space.spacewall.dto.SpaceWallRequest.CreateDto;
import com.fastcampus.jober.domain.space.spacewall.dto.SpaceWallResponse.ResponseDto;
import com.fastcampus.jober.domain.space.spacewall.service.SpaceWallService;
import com.fastcampus.jober.domain.space.spacewall.spacewallpermission.service.SpaceWallPermissionService;
import com.fastcampus.jober.global.security.auth.session.MemberDetails;
import com.fastcampus.jober.global.constant.Auths;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/new-spaces")
@RequiredArgsConstructor
public class SpaceWallCreationController {

    private final SpaceWallService spaceWallService;
    private final SpaceWallPermissionService permissionService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ResponseDto>> create(
            @RequestBody CreateDto createDto
    ) {
        MemberDetails memberDetails =
                (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentMemberId = memberDetails.getMemberId();

        ResponseDto createdSpaceWallDto = spaceWallService.create(createDto, currentMemberId);

        if (createdSpaceWallDto == null) {
            return ResponseEntity.badRequest().body(null);
        }

        permissionService.assignPermissionToSpaceWall(Auths.OWNER, createdSpaceWallDto.getId(), currentMemberId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(HttpStatus.CREATED, "공유페이지가 생성되었습니다.", createdSpaceWallDto));
    }

//    @PostMapping("/empty")
//    public ResponseEntity<ResponseDTO<SpaceWallResponse.EmptySpaceResponseDto>> createEmptySpace(
//            @RequestBody SpaceWallRequest.CreateDto createDto,
//            @AuthenticationPrincipal MemberDetails memberDetails) {
//
//        SpaceWallResponse.EmptySpaceResponseDto createdEmptySpaceDto = spaceWallService.createEmptySpace(createDto, memberDetails);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(HttpStatus.CREATED, "빈 공유페이지가 생성되었습니다.", createdEmptySpaceDto));
//    }
}
