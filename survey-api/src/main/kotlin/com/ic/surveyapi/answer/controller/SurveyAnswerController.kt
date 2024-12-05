package com.ic.surveyapi.answer.controller

import com.ic.surveyapi.answer.controller.dto.SurveyAnswerRequest
import com.ic.surveyapi.answer.controller.dto.SurveyFormAnswerResponse
import com.ic.surveyapi.answer.service.SurveyAnswerService
import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveyapi.util.ObjectMapperUtil
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_SURVEY_PREFIX)
class SurveyAnswerController(
    private val surveyAnswerService: SurveyAnswerService,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{surveyFormId}/answers")
    fun submitSurveyAnswer(
        @RequestBody @Validated surveyAnswerRequest: SurveyAnswerRequest,
        @PathVariable(name = "surveyFormId", required = true) surveyFormId: String,
    ) = run { objectMapperUtil.convertClass(value = surveyAnswerRequest, clazz = SurveyAnswerDto::class.java) }
        .let { surveyAnswerService.submitSurveyAnswer(surveyFormId = surveyFormId, surveyAnswer = it) }

    // 해당 특정 설문 조사를 조회
    @GetMapping("/{surveyFormId}/answers")
    fun getSurveyAnswers(
        @PathVariable(name = "surveyFormId", required = true) surveyFormId: String,
    ): List<SurveyFormAnswerResponse> =
        run { surveyAnswerService.getSurveyAnswerBySurveyFormId(surveyFormId = surveyFormId) }
            .map { objectMapperUtil.convertClass(value = it, clazz = SurveyFormAnswerResponse::class.java) }

    // 설문 조사 Title 기반으로 설문 조사를 조회
    @GetMapping("/surveys/{surveyTitle}")
    fun getSurveyAnswersByTitle(@PathVariable(name = "surveyTitle", required = true) surveyTitle: String) =
        let { surveyAnswerService.getSurveyAnswerBySurveyFormId(surveyTitle) }

}
