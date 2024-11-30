package org.icd.surveyapi.surveyproducer.presentation

import org.icd.surveyapi.surveyproducer.application.SurveyProducerService
import org.icd.surveyapi.surveyproducer.application.dto.request.PostSurveyRequest
import org.icd.surveyapi.surveyproducer.application.dto.response.GetSurveyResponse
import org.icd.surveyapi.surveyproducer.application.dto.response.PostSurveyResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/survey")
class SurveyProducerController(
    private val surveyService: SurveyProducerService
) {
    @PostMapping
    fun postSurvey(@RequestBody request: PostSurveyRequest): PostSurveyResponse {
        return surveyService.postSurvey(request)
    }

    @GetMapping("/{surveyId}")
    fun getSurvey(@PathVariable surveyId: Long): GetSurveyResponse {
        return surveyService.getSurvey(surveyId)
    }

}