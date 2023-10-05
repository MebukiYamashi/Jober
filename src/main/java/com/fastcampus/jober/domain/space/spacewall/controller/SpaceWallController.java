package com.fastcampus.jober.domain.space.spacewall.controller;

import com.fastcampus.jober.domain.space.spacewall.dto.SpaceWallRequest;
import com.fastcampus.jober.domain.space.spacewall.dto.SpaceWallRequest.UpdateDto;
import com.fastcampus.jober.domain.space.spacewall.dto.SpaceWallResponse.ResponseDto;
import com.fastcampus.jober.domain.space.spacewall.dto.SpaceWallResponse.SessionDTO;
import com.fastcampus.jober.domain.space.spacewall.service.SpaceWallService;

import com.fastcampus.jober.global.security.auth.session.MemberDetails;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spaces")
@RequiredArgsConstructor
public class SpaceWallController {

    private final SpaceWallService spaceWallService;

    @GetMapping("/view/{id}")
    public ResponseEntity<ResponseDTO<ResponseDto>> getSpaceWall(
        @PathVariable Long id) {
        ResponseDto foundSpaceWallDto = spaceWallService.findById(id);

        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "완료되었습니다.", foundSpaceWallDto));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseDTO<ResponseDto>> updateSpaceWall(
        @PathVariable Long id,
        @RequestBody UpdateDto updateDto,
        @AuthenticationPrincipal MemberDetails memberDetails) {

        ResponseDto updatedSpaceWallDto = spaceWallService.update(id, updateDto,
            memberDetails);

        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "수정되었습니다.", updatedSpaceWallDto));
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteSpaceWall(
        @PathVariable Long id,
        @AuthenticationPrincipal MemberDetails memberDetails) {

        spaceWallService.delete(id, memberDetails);

        return ResponseEntity.ok(
            new ResponseDTO<>(HttpStatus.OK, "공유페이지 " + id + " 삭제되었습니다.", "Success"));
    }

    @PostMapping("/check/{id}")
    public ResponseEntity<?> checkEditMode(@PathVariable Long id, HttpSession session,
        @AuthenticationPrincipal
        MemberDetails memberDetails) {
        SessionDTO sessionDTO =
                spaceWallService.checkEditSession(memberDetails.getMember().getId(), id, session);

        return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "정상적으로 처리되었습니다.", sessionDTO),
            HttpStatus.OK);
    }

    @PutMapping("/url/{id}")
    public ResponseEntity<ResponseDTO<String>> urlModify(
        @Valid @RequestBody SpaceWallRequest.UrlUpdateDto urlUpdateDto,
        @PathVariable Long id) {
        String updateUrl = spaceWallService.modifyUrl(urlUpdateDto, id);

        return new ResponseEntity<>(
            new ResponseDTO<>(HttpStatus.OK, "정상적으로 처리되었습니다.", updateUrl),
            HttpStatus.OK);
    }

    @PutMapping("/show/{id}")
    public ResponseEntity<ResponseDTO<String>> authorizedModify(@PathVariable Long id,
        @RequestParam boolean authorized) {

        spaceWallService.modifyAuthorized(id, authorized);

        return new ResponseEntity<>(new ResponseDTO<>(HttpStatus.OK, "정상적으로 처리되었습니다.", null),
            HttpStatus.OK);
    }
}