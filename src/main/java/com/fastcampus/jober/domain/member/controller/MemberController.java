package com.fastcampus.jober.domain.member.controller;

import com.fastcampus.jober.domain.member.dto.MemberRequest;
import com.fastcampus.jober.domain.member.service.MemberService;
import com.fastcampus.jober.global.security.auth.jwt.JwtTokenProvider;
import com.fastcampus.jober.global.error.exception.MemberException;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.fastcampus.jober.domain.member.dto.MemberResponse.JoinDTO;
import static com.fastcampus.jober.domain.member.dto.MemberResponse.MySpaceWallDTO;
import static com.fastcampus.jober.global.constant.ErrorCode.DUPLICATED_EMAIL;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 합니다.
     * @param request 회원가입 요청 정보
     * @return 사용자 정보 반환
     */
    @PostMapping("/join")
    public ResponseEntity<ResponseDTO<JoinDTO>> join(
        @Valid @RequestBody MemberRequest.JoinDTO request
    ) {
        if (memberService.checkEmailDuplication(request.getEmail()))
            throw new MemberException(DUPLICATED_EMAIL);

        return ResponseEntity
                .ok(new ResponseDTO<>(memberService.join(request), "회원가입에 성공했습니다."));
    }

    /**
     * 로그인 합니다.
     * @param request 로그인 요청 정보
     * @return Header(토큰), Body(사용자 정보) 반환
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<Object>> login(
        @RequestBody @Valid MemberRequest.LoginDTO request
    ) {
        Map<String, Object> response = memberService.login(request.getEmail(), request.getPassword());

        return ResponseEntity
                .ok()
                .header(JwtTokenProvider.HEADER, (String) response.get("token"))
                .body(new ResponseDTO<>(response.get("memberInfo"), "로그인에 성공했습니다."));
    }

    /**
     * 토큰 기한을 0으로 축소시켜 로그아웃 합니다.
     * @param authorization 토큰
     * @return 메세지 반환
     */
    @PostMapping("/api-logout")
    public ResponseEntity<ResponseDTO<String>> logout(
        @RequestHeader(JwtTokenProvider.HEADER) String authorization
    ) {
        memberService.logout(authorization.split(" ")[1]); // Bearer 떼냄.
        return ResponseEntity
                .ok(new ResponseDTO<>("로그아웃 하였습니다."));
    }

    /**
     * Email이 Member에 존재하는지 확인하기 위한 API 입니다.
     * @param request String email
     * @return boolean
     */
    @GetMapping("/check-email/{email}")
    public boolean isExistMemberByEmail(
            @Email @PathVariable(name = "email") String request
    ) {
        return memberService.checkEmailDuplication(request);
    }

    /**
     * 토큰 검사
     * @param authorization 토큰 정보
     * @return 이상이 없는 경우 true
     */
    @GetMapping("/check-token")
    public boolean isOKToken(
            @RequestHeader(JwtTokenProvider.HEADER) String authorization
    ) {
        return JwtTokenProvider.validateToken(authorization.split(" ")[1]);
    }

    /**
     * 사용자가 공동작업자로 속한 공유스페이스 목록 중 최상위 depth의 것을 조회합니다.
     * @return 공유스페이스 목록
     */
    @GetMapping("/my-spaces")
    public ResponseEntity<ResponseDTO<List<MySpaceWallDTO>>> mySpacesList() {
        return ResponseEntity
                .ok(new ResponseDTO<>(
                        memberService.findMySpaceWalls(),
                        "사용자가 공동 작업자로 속한 공유스페이스 목록을 조회합니다.")
                );
    }
}