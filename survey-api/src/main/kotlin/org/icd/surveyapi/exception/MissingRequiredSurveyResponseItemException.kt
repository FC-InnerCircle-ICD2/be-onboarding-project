package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

class MissingRequiredSurveyResponseItemException : ApplicationException(
    httpStatus = HttpStatus.BAD_REQUEST,
    errorCode = ErrorCode.MISSING_REQUIRED_SURVEY_ITEM_RESPONSE,
)