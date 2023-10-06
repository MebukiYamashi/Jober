package com.fastcampus.jober.global.error.advice;

import com.fastcampus.jober.global.error.exception.DomainException;
import com.fastcampus.jober.global.error.exception.Exception401;
import com.fastcampus.jober.global.error.exception.Exception403;
import com.fastcampus.jober.global.error.exception.Exception500;
import com.fastcampus.jober.global.error.exception.ExceptionValid;
import com.fastcampus.jober.global.error.exception.TokenException;
import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class MyExceptionAdvice {

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> handleDomainException(DomainException e) {

        ResponseDTO<String> response = new ResponseDTO<>(e.getHttpStatus(), e.getMessage());

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        ExceptionValid ev = new ExceptionValid(
            e.getBindingResult().getFieldError().getField(),
            e.getBindingResult().getFieldError().getDefaultMessage()
        );
        return new ResponseEntity<>(ev.body(), ev.status());
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ResponseDTO<String>> handleMethodNotAllowedException(
        MethodNotAllowedException e) {
        ResponseDTO<String> response = new ResponseDTO<>(HttpStatus.METHOD_NOT_ALLOWED,
            e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ResponseDTO<String>> handleTokenException(TokenException e) {

        ResponseDTO<String> response = new ResponseDTO<>(e.getHttpStatus(), e.getMessage());

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {

        ResponseDTO<String> response = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR,
            e.getMessage());

        log.error(e.getMessage(), e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
