package com.ic.surveyapi.answer.controller

import com.ic.surveyapi.answer.controller.dto.SurveyAnswerRequest
import com.ic.surveyapi.answer.service.SurveyAnswerService
import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveyapi.util.ObjectMapperUtil
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_SURVEY_PREFIX)
class SurveyAnswerController(
    private val surveyAnswerService: SurveyAnswerService,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    @PostMapping("/{surveyFormId}/answers")
    fun submitSurveyAnswer(
        @RequestBody surveyAnswerRequest: SurveyAnswerRequest,
        @PathVariable(name = "surveyFormId", required = true) surveyFormId: String,
    ) {
        surveyAnswerService.submitSurveyAnswer(
            surveyFormId = surveyFormId,
            surveyAnswer =
                objectMapperUtil.convertClass(
                    value = surveyAnswerRequest,
                    clazz = SurveyAnswerDto::class.java,
                ),
        )
    }
}
