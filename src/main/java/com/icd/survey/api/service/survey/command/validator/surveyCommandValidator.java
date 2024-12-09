package com.icd.survey.api.service.survey.command.validator;

import com.icd.survey.api.service.survey.command.CreateSurveyCommand;
import com.icd.survey.exception.ApiException;
import com.icd.survey.exception.response.emums.ExceptionResponseType;

public class surveyCommandValidator {


    public void throwApiException(ExceptionResponseType responseType) {
        throw new ApiException(responseType);
    }

    public void throwApiException(ExceptionResponseType responseType, String message) {
        throw new ApiException(responseType, message);
    }
}
