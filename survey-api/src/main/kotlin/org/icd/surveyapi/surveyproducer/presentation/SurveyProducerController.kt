package org.icd.surveyapi.surveyproducer.presentation

import org.icd.surveyapi.surveyproducer.application.SurveyProducerService
import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyRequest
import org.icd.surveyapi.surveyproducer.application.dto.response.PostSurveyResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/survey")
class SurveyProducerController(
    private val surveyService: SurveyProducerService
) {
    @PostMapping
    fun postSurvey(@RequestBody request: PostSurveyRequest): PostSurveyResponse {
        return surveyService.postSurvey(request)
    }

}