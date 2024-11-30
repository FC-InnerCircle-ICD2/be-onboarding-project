package com.ic.surveyapi.answer.controller

import com.ic.surveyapi.answer.controller.dto.SurveyAnswerRequest
import com.ic.surveyapi.answer.service.SurveyAnswerService
import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveyapi.util.ObjectMapperUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@RestController(ApiEndpointVersionPrefix.V1_API_SURVEY_PREFIX)
class SurveyAnswerController(
    private val surveyAnswerService: SurveyAnswerService,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    @GetMapping("/{surveyId}/answers")
    fun submitSurveyAnswer(
        @RequestBody surveyAnswerRequest: SurveyAnswerRequest,
        @PathVariable(name = "surveyId") surveyId: String,
    ) {
        surveyAnswerService.submitSurveyAnswer(
            surveyAnswer =
                objectMapperUtil.convertClass(
                    value = surveyAnswerRequest,
                    clazz = SurveyAnswerDto::class.java,
                ),
        )
    }
}
