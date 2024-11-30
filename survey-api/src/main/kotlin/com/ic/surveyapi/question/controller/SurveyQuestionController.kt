package com.ic.surveyapi.question.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@RestController(ApiEndpointVersionPrefix.V1_API_SURVEY_PREFIX)
class SurveyQuestionController {

    @GetMapping("/{surveyId}/responses")
    fun surveyResponses() {

    }
}
