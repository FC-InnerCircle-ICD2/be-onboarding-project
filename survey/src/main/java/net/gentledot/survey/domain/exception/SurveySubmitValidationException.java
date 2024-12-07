package net.gentledot.survey.domain.exception;

public class SurveySubmitValidationException extends SurveyServiceException {
    public SurveySubmitValidationException(ServiceError serviceError) {
        super(serviceError);
    }

    public SurveySubmitValidationException(ServiceError serviceError, Throwable cause) {
        super(serviceError, cause);
    }
}
