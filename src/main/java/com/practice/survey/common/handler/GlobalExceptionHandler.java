package com.practice.survey.common.handler;

import com.practice.survey.common.response.ApiResponse;
import com.practice.survey.common.response.StatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse methodValidException(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest) {

        log.error("MethodArgumentNotValidException url: {}", httpServletRequest.getRequestURI(), exception);

        ValidationException errorResponse = makeErrorResponse(exception.getBindingResult());

        return ApiResponse.builder().build().serverError(errorResponse.getCode(), errorResponse.getMessage());
    }

    private ValidationException makeErrorResponse(BindingResult bindingResult) {
        int code = StatusEnum.INTERNAL_SERVER_ERROR.getCode();
        String message = StatusEnum.INTERNAL_SERVER_ERROR.getMessage();

        if (bindingResult.hasErrors()) {
            String bindResultCode = bindingResult.getFieldError().getCode();

            switch (bindResultCode) {
                case "NotNull":
                    code = StatusEnum.NOT_NULL_VIOLATE.getCode();
                    message = StatusEnum.NOT_NULL_VIOLATE.getMessage();
                    break;
                case "Size":
                    code = StatusEnum.DATA_LENGTH_VIOLATE.getCode();
                    message = StatusEnum.DATA_LENGTH_VIOLATE.getMessage();
                    break;
                case "NotEmpty":
                    code = StatusEnum.NOT_EMPTY_VIOLATE.getCode();
                    message = StatusEnum.NOT_EMPTY_VIOLATE.getMessage();
                    break;
            }
            String bindResultMessage = bindingResult.getFieldError().getDefaultMessage();
            if(bindResultMessage != null) {
                message = bindResultMessage;  // 유효성 검사에 설정된 메시지 반환
            }
        }
        return new ValidationException(code, message);
    }


    @ExceptionHandler(Exception.class)
    public ApiResponse handleAllExceptions(Exception exception, HttpServletRequest request) {
        log.error("Unexpected Exception: {}", exception);
        return ApiResponse.builder().build().serverError(StatusEnum.INTERNAL_SERVER_ERROR);
    }
}
