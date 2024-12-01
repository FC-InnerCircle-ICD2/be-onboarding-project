package com.innercircle.surveryproject.infra.exceptions;

import com.innercircle.surveryproject.modules.global.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * global exception handler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 사용자 입력 오류시 해당에러 리턴
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity invalidInputException(Exception ex) {
        return ResponseUtils.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * exception 발생 시 에러처리
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity exception(Exception ex) {
        return ResponseUtils.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
