package net.gentledot.survey.domain.exception;

public class SurveyCreationException extends SurveyServiceException {
    public SurveyCreationException(ServiceError serviceError) {
        super(serviceError);
    }

    public SurveyCreationException(ServiceError serviceError, Throwable cause) {
        super(serviceError, cause);
    }
}
