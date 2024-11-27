package org.icd.surveyapi.exception

import org.springframework.http.HttpStatus

class DuplicateSurveyItemSequenceException: ApplicationException(
    httpStatus = HttpStatus.BAD_REQUEST,
    errorCode = ErrorCode.DUPLICATE_SURVEY_ITEM_SEQUENCE
)
