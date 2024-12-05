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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_SURVEY_PREFIX)
class SurveyAnswerController(
    private val surveyAnswerService: SurveyAnswerService,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    // TODO : URL Path 들 다시 고민해보자 .... !
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
    ): SurveyFormAnswerResponse =
        run { surveyAnswerService.getSurveyAnswerBySurveyFormId(surveyFormId = surveyFormId) }
            .let { objectMapperUtil.convertClass(value = it, clazz = SurveyFormAnswerResponse::class.java) }

    // 설문 조사 Title 기반으로 설문 조사를 조회
    @GetMapping("/answers")
    fun getSurveyAnswersBySurveyTitle(
        @RequestParam(name = "surveyTitle", required = true
    ) surveyTitle: String) =
        let { surveyAnswerService.getSurveyAnswerByTitle(surveyTitle) }

    // TODO - 응답 항목의 이름과 응답 값을 기반으로 검색 가능하도록 구현 필요

}
