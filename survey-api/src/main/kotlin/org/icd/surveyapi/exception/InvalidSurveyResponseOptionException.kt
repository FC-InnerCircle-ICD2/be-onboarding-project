package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

class InvalidSurveyResponseOptionException : ApplicationException(
    httpStatus = HttpStatus.BAD_REQUEST,
    errorCode = ErrorCode.INVALID_SURVEY_RESPONSE_OPTION
)
