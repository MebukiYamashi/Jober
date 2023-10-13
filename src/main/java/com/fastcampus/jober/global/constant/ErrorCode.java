package com.fastcampus.jober.global.constant;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    INVALID_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 id값입니다"),
    INVALID_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증되지 않았습니다"), // 비로그인
    INVALID_ACCESS(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 접근입니다"),
    INVALID_USER(HttpStatus.FORBIDDEN, "권한이 없습니다"), // 설정한 권한과 맞지 않은 권한
    NOT_ALLOWED_DELETE_SPACE_WALL_MEMBER(HttpStatus.BAD_REQUEST, "자기 자신 또는 생성자를 제외할 수 없습니다."),

    // 계정 관련 오류
    EMPTY_ID(HttpStatus.BAD_REQUEST, "아이디를 입력해주세요"),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 입력해주세요"),
    EXISTING_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다"),
    CHECK_ID_PASSWORD(HttpStatus.BAD_REQUEST, "아이디와 비밀번호를 확인해주세요"),
    CHECK_ID(HttpStatus.BAD_REQUEST, "아이디를 확인해주세요"),
    CHECK_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요"),
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "해당 사용자를 찾을 수 없습니다."),
    TEMPLATE_NOT_FOUND(HttpStatus.BAD_REQUEST, "템플릿 정보를 찾을 수 없습니다."),
    TEMPLATE_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "템플릿 분류를 확인해주세요."),
    PAGE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "페이지 번호는 0보다 작을 수 없습니다."),
    MY_TEMPLATE_NOT_FOUND(HttpStatus.BAD_REQUEST, "내 템플릿 정보를 찾을 수 없습니다."),
    MY_TEMPLATE_MEMBER_NOT_MATCHED(HttpStatus.BAD_REQUEST, "본인의 템플릿만 해제가 가능합니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자 정보를 찾을 수 없습니다."),

    // 토큰 오류
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료 되었습니다."),
    ILLEGAL_ARGUMENT(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰입니다."),
    NOT_FOUNT_JWT(HttpStatus.UNAUTHORIZED, "토큰이 누락되었습니다."),
    DUPLICATED_EMAIL(HttpStatus.UNAUTHORIZED, "중복된 이메일입니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 서명입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    UNKNOWN_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "알 수 없는 토큰 에러가 발생했습니다."),
    SPACEWALL_NOT_FOUND(HttpStatus.BAD_REQUEST, "공유페이지를 찾을 수 없습니다."),
    COMPONENTTEMP_NOT_FOUND(HttpStatus.BAD_REQUEST, "임시 컴포넌트를 찾을 수 없습니다."),
    INVALID_COMPONENT_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 type입니다"),
    INVALID_COMPONENTTEMPID(HttpStatus.BAD_REQUEST, "유효하지 않은 componentTempId입니다"),
    DTO_NOT_EXISTS(HttpStatus.BAD_REQUEST, "DTO가 넘어오지 않았습니다."),
    CANT_SHOW_COMPENTTEMP(HttpStatus.BAD_REQUEST, "조회 불가능한 타입입니다."),
    INVALID_SPACEWALLID(HttpStatus.BAD_REQUEST, "유효하지 않은 spaceWallId값입니다"),
    ALREADY_EXSIST_SPACEWALLTEMP(HttpStatus.BAD_REQUEST, "편집 중이던 SpaceWallTemp가 존재합니다."),
    SPACE_WALL_NOT_FOUND(HttpStatus.BAD_REQUEST,"ID가 있는 공유페이지을 찾을 수 없습니다: "),
    RUNTIME_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    SPACEWALL_NO_PERMISSION_TO_DELETE(HttpStatus.BAD_REQUEST, "이 공유페이지를 삭제할 권한이 없습니다."),
    SPACEWALL_CANNOT_DELETE_ROOT(HttpStatus.BAD_REQUEST, "최상단의 공유페이지는 삭제할 수 없습니다."),
    SPACEWALL_HISTORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "히스토리를 찾을 수 없습니다."),
    HISTORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 회원의 최근 히스토리가 존재하지 않습니다."),
    INVALID_SPACEWALL_PERMISSION_ID(HttpStatus.BAD_REQUEST, "잘못된 공유페이스 권한 ID입니다."),
    INVALID_SPACEWALL_ID(HttpStatus.BAD_REQUEST, "잘못된 공유페이스 ID입니다."),
    NO_MOVE_PERMISSION(HttpStatus.FORBIDDEN, "이 페이지를 이동시킬 권한이 없습니다."),
    INVALID_TARGET_SEQUENCE(HttpStatus.BAD_REQUEST, "잘못된 타겟 순서입니다."),
    UNNECESSARY_MOVE_OPERATION(HttpStatus.BAD_REQUEST, "이동할 필요가 없습니다."),
    INVALID_HISTORY_ACCESS(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 접근입니다."),
    BAD_SPACE_WALL_REQUEST(HttpStatus.BAD_REQUEST,"공유페이지 요청이 잘못되었습니다."),
    NOT_EXSIST_SPACEWALLTEMP(HttpStatus.BAD_REQUEST, "편집 중이던 SpaceWallTemp가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return httpStatus.value();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
