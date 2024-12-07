package ziwookim.be_onboarding_project.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ziwookim.be_onboarding_project.common.response.BadRequestResponse;
import ziwookim.be_onboarding_project.common.response.CommonResponse;
import ziwookim.be_onboarding_project.common.web.enums.HttpErrors;
import ziwookim.be_onboarding_project.common.web.error.HttpError;
import ziwookim.be_onboarding_project.common.web.exception.BadRequestException;
import ziwookim.be_onboarding_project.common.web.exception.NotFoundException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestController
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {
            NotFoundException.class,
            NoHandlerFoundException.class,
            NoResourceFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class
    })
    public ResponseEntity<BadRequestResponse> notFoundException(Exception e) {
        HttpError notFoundHttpError = HttpErrors.NOT_FOUND;
        return ResponseEntity.status(notFoundHttpError.getStatus()).body(BadRequestResponse.of(notFoundHttpError));
    }

    @ExceptionHandler(value = {
            BadRequestException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<CommonResponse> badRequestException(Exception exception, HttpServletRequest request) {
        log.error("", exception);

        if (exception instanceof BadRequestException e) {
            HttpError httpError = e.getError();
            return ResponseEntity.status(httpError.getStatus()).body(BadRequestResponse.of(httpError));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BadRequestResponse.of());
    }
}
