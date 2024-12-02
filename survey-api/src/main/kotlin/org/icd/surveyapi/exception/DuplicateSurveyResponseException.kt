package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

class DuplicateSurveyResponseException : ApplicationException(
    httpStatus = HttpStatus.CONFLICT,
    errorCode = ErrorCode.DUPLICATE_SURVEY_RESPONSE
)
