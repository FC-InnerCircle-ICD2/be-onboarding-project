package com.practice.survey.common.handler;

import com.practice.survey.common.response.ResponseTemplate;
import com.practice.survey.common.response.StatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // javax.validation.Valid or @Validated 의 binding error 로 발생하는 예외 처리
    // DTO, 요청 본문(JSON) 등의 필드 유효성 검사 실패 시 발생
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseTemplate methodValidException(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest) {

        log.error("MethodArgumentNotValidException url: {}", httpServletRequest.getRequestURI(), exception);

        ValidationException errorResponse = makeErrorResponse(exception.getBindingResult());

        return ResponseTemplate.builder().build().serverError(errorResponse.getCode(), errorResponse.getMessage());
    }

    // 유효성 검사 실패 시 응답 생성
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

            // 사용자 정의 메시지가 있으면 적용
            String bindResultMessage = bindingResult.getFieldError().getDefaultMessage();
            if(bindResultMessage != null) {
                message = bindResultMessage;  // 유효성 검사에 설정된 메시지 반환
            }
        }
        return new ValidationException(code, message);
    }

    // 지원되지 않는 HTTP 메서드 호출 시 발생
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseTemplate handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        log.error("Unsupported HTTP Method: {}, URL: {}", exception.getMethod(), request.getRequestURI());
        return ResponseTemplate.builder()
                .build()
                .serverError(StatusEnum.METHOD_NOT_SUPPORTED.getCode(), "지원하지 않는 HTTP 메서드입니다.");
    }

    // 지원되지 않는 콘텐츠 타입 요청 시 발생
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseTemplate handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception, HttpServletRequest request) {
        log.error("Unsupported Media Type: {}, URL: {}", exception.getContentType(), request.getRequestURI());
        return ResponseTemplate.builder()
                .build()
                .serverError(StatusEnum.UNSUPPORTED_MEDIA_TYPE.getCode(), "지원하지 않는 미디어 타입입니다.");
    }

    // 요청 파라미터가 없을 때 발생
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseTemplate handleMissingRequestParameterException(MissingServletRequestParameterException exception, HttpServletRequest request) {
        log.error("Missing Request Parameter: {}, URL: {}", exception.getParameterName(), request.getRequestURI());
        return ResponseTemplate.builder()
                .build()
                .serverError(StatusEnum.MISSING_PARAMETER.getCode(), "필수 요청 파라미터가 누락되었습니다: " + exception.getParameterName());
    }

    // enum type 일치하지 않아 binding 못할 경우 발생
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseTemplate handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        log.error("Method Argument Type Mismatch: {}, URL: {}", exception.getName(), request.getRequestURI());
        return ResponseTemplate.builder()
                .build()
                .serverError(StatusEnum.INPUT_TYPE_MISMATCH.getCode(), "입력 유형이 일치하지 않습니다.");
    }

    // 메서드 수준 유효성 검사 (메서드 파라미터, 경로 변수 등)
    // 메서드 호출 시 유효성 검사가 실패할 때 발생
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseTemplate<?> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        log.error("ConstraintViolationException at URL: {}", request.getRequestURI(), exception);

        return ResponseTemplate.builder()
                .build()
                .serverError(StatusEnum.VALIDATION_ERROR.getCode(), "유효성 검사에 실패했습니다: " + exception.getMessage());
    }

    // 그 외 모든 예외에 대한 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseTemplate handleAllExceptions(Exception exception, HttpServletRequest request) {
        log.error("Unexpected Exception: {}", exception);
        return ResponseTemplate.builder().build().serverError(StatusEnum.INTERNAL_SERVER_ERROR);
    }
}
