package net.gentledot.survey.exception;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.dto.common.ServiceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MVC_ERROR_FLAG = "=== MVC 오류 ===";

    @ExceptionHandler(SurveyCreationException.class)
    public ResponseEntity<Object> handleSurveyCreationException(SurveyCreationException e) {
        log.warn("===서베이 생성 오류 ===", e);
        ServiceResponse<?> fail = createFail(e.getServiceError());
        return createServiceResponse(fail, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn(MVC_ERROR_FLAG, e);
        ServiceResponse<?> fail = createFail(ServiceError.BAD_REQUEST);
        return createServiceResponse(fail, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn(MVC_ERROR_FLAG, e);
        ServiceResponse<?> fail = createFail(ServiceError.BAD_REQUEST);
        return createServiceResponse(fail, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn(MVC_ERROR_FLAG, e);
        ServiceResponse<?> fail = createFail(ServiceError.BAD_REQUEST);
        return createServiceResponse(fail, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn(MVC_ERROR_FLAG, e);
        ServiceResponse<?> fail = createFail(ServiceError.BAD_REQUEST);
        return createServiceResponse(fail, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception e) {
        log.error("=== 다뤄지지 않은 오류 발생 ===", e);
        ServiceResponse<?> fail = createFail(ServiceError.INTERNAL_SERVER_ERROR);
        return createServiceResponse(fail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ServiceResponse<?> createFail(ServiceError e) {
        return ServiceResponse.fail(e);
    }

    private ResponseEntity<Object> createServiceResponse(ServiceResponse failedResponse, HttpStatus status) {
        return new ResponseEntity<>(failedResponse, status);
    }
}
