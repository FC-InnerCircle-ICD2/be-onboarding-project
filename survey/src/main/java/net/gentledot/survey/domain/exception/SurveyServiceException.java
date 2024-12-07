package net.gentledot.survey.domain.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SurveyServiceException extends RuntimeException {
    private final ServiceError serviceError;

    public SurveyServiceException(ServiceError serviceError) {
        super(serviceError.getMessage());
        this.serviceError = serviceError;
    }

    public SurveyServiceException(ServiceError serviceError, Throwable cause) {
        super(serviceError.getMessage(), cause);
        this.serviceError = serviceError;
    }
}
