package com.ic.surveyapi.controller

import com.ic.surveyapi.service.SurveyService
import com.ic.surveydata.entity.SurveyForm
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_PREFIX)
class SurveyController(
    private val surveyService: SurveyService,
) {
    @PostMapping("/surveys")
    fun createSurveyForms(@RequestBody surveyForm: SurveyForm): SurveyForm {
        // TODO - Survey Form 을 생성
        surveyService.createSurvey(surveyForm = SurveyForm())

        return surveyForm
    }
}
