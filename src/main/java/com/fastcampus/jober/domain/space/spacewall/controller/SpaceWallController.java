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

    /**
     * ID를 통해 공유스페이스 정보를 조회합니다.
     * @param id 공유스페이스 ID
     * @return 공유스페이스 정보 반환
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<ResponseDTO<ResponseDto>> getSpaceWall(
        @PathVariable Long id) {
        ResponseDto foundSpaceWallDto = spaceWallService.findById(id);

        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "완료되었습니다.", foundSpaceWallDto));
    }

    /**
     * ID를 통해 공유스페이스 정보를 수정합니다.
     * @param id 공유스페이스 ID
     * @param updateDto 공유스페이스 수정 정보
     * @param memberDetails 사용자 정보
     * @return 수정된 공유스페이스 정보 반환
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseDTO<ResponseDto>> updateSpaceWall(
        @PathVariable Long id,
        @RequestBody UpdateDto updateDto,
        @AuthenticationPrincipal MemberDetails memberDetails) {

        ResponseDto updatedSpaceWallDto = spaceWallService.update(id, updateDto,
            memberDetails);

        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK, "수정되었습니다.", updatedSpaceWallDto));
    }

    /**
     * ID를 통해 공유스페이스 정보를 삭제합니다.
     * @param id 공유스페이스 ID
     * @param memberDetails 사용자 정보
     * @return 삭제 성공 메세지 반환
     */
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