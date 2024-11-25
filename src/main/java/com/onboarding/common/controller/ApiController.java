package com.onboarding.common.controller;

import com.onboarding.common.dto.ErrorResponse;
import com.onboarding.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApiController {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse baseException(BaseException e) {
        return new ErrorResponse(e.isSuccess(), e.getMessage());
    }
}
