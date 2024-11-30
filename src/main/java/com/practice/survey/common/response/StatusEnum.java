package com.practice.survey.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusEnum {

    SUCCESS(200, "Success", HttpStatus.OK)
    , INVALID_REQUEST(400, "Invalid Request", HttpStatus.BAD_REQUEST)
    , INTERNAL_SERVER_ERROR(500, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR)
    , NOT_FOUND(404, "Not Found", HttpStatus.NOT_FOUND);

    private int code;

    private String message;

    private HttpStatus status;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    StatusEnum(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
