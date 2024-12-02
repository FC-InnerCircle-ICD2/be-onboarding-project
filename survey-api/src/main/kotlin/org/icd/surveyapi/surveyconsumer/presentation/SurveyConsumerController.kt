package org.icd.surveyapi.surveyconsumer.presentation

import org.icd.surveyapi.surveyconsumer.application.SurveyConsumerService
import org.icd.surveyapi.surveyconsumer.application.dto.request.PostSurveyResponseRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/survey")
class SurveyConsumerController(
    private val surveyConsumerService: SurveyConsumerService
) {
    @PostMapping("/{surveyId}")
    fun postSurveyResponse(@PathVariable surveyId: Long, @RequestBody request: PostSurveyResponseRequest): String {
        return surveyConsumerService.postSurveyResponse(surveyId, request)
    }
}