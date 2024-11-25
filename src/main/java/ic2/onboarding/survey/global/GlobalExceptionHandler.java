package ic2.onboarding.survey.global;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResult<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        logging(e);

        Map<String, String> validations = new HashMap<>();

        e.getFieldErrors()
                .forEach(error -> validations.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .status(ErrorCode.NOT_VALIDATED.getHttpStatus())
                .body(new ApiResult<>(ErrorCode.NOT_VALIDATED, validations));
    }


    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiResult<Void>> handleConstraintViolationException(ConstraintViolationException e) {

        logging(e);

        Map<String, String> validations = new HashMap<>();

        e.getConstraintViolations()
                .forEach(constraintViolation -> validations.put(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().getName(), constraintViolation.getMessage()));

        return ResponseEntity
                .status(ErrorCode.NOT_VALIDATED.getHttpStatus())
                .body(new ApiResult<>(ErrorCode.NOT_VALIDATED, validations));
    }


    @ExceptionHandler(BizException.class)
    protected ResponseEntity<ApiResult<Void>> handleBizException(BizException e) {
        logging(e);

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(new ApiResult<>(e.getErrorCode()));
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResult<Void>> handleException(Exception e) {
        logging(e);

        return ResponseEntity
                .internalServerError()
                .body(new ApiResult<>(ErrorCode.SERVER_ERROR));
    }


    private static void logging(Exception e) {

        log.warn("[{}]", e.getClass().getSimpleName(), e);
    }

}
