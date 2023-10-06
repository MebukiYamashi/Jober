package com.fastcampus.jober.global.utils.api.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDTO<T> {

    private Integer code; // 에러시에 의미 있음.
    private String message; // 에러시에 의미 있음. ex) badRequest
    private T data; // 에러시에는 구체적인 에러 내용 ex) email 이 입력되지 않았습니다

    public ResponseDTO() {
        this.code = HttpStatus.OK.value();
        this.message = "정상적으로 처리되었습니다.";
    }

    public ResponseDTO(T data) {
        this.code = HttpStatus.OK.value();
        this.message = "정상적으로 처리되었습니다.";
        this.data = data;
    }

    public ResponseDTO(HttpStatus httpStatus, String message, T data) {
        this.code = httpStatus.value();
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(String message) {
        this.code = HttpStatus.OK.value();
        this.message = message;
    }

    public ResponseDTO(T data, String message) {
        this.code = HttpStatus.OK.value();
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }
}
