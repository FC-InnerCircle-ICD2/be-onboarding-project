package com.ic.surveyapi.answer.controller

import com.ic.surveyapi.answer.controller.dto.SurveyAnswerRequest
import com.ic.surveyapi.answer.controller.dto.SurveyAnswerSearchResponse
import com.ic.surveyapi.answer.controller.dto.SurveyFormAnswerResponse
import com.ic.surveyapi.answer.service.SurveyAnswerService
import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveyapi.exception.controller.CommonErrorResponse
import com.ic.surveyapi.util.ObjectMapperUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@Tag(name = "Survey Answer", description = "설문 답변 관련 API를 제공합니다.")
@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_SURVEY_PREFIX)
class SurveyAnswerController(
    private val surveyAnswerService: SurveyAnswerService,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    @Operation(
        summary = "설문 답변 제출",
        description = "특정 설문 양식에 대한 답변을 제출합니다.",
        responses = [
            ApiResponse(responseCode = "201", description = "답변이 성공적으로 제출되었습니다."),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청입니다.",
                content = [Content(schema = Schema(implementation = CommonErrorResponse::class))],
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 설문 양식을 찾을 수 없습니다.",
                content = [Content(schema = Schema(implementation = CommonErrorResponse::class))],
            ),
        ],
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{surveyFormId}/answers")
    fun submitSurveyAnswer(
        @RequestBody @Validated
        @Parameter(description = "제출할 설문 답변", required = true)
        surveyAnswerRequest: SurveyAnswerRequest,
        @PathVariable(name = "surveyFormId", required = true)
        @Parameter(description = "설문 양식 ID", required = true)
        surveyFormId: String,
    ) = run { objectMapperUtil.convertClass(value = surveyAnswerRequest, clazz = SurveyAnswerDto::class.java) }
        .let { surveyAnswerService.submitSurveyAnswer(surveyFormId = surveyFormId, surveyAnswer = it) }

    @Operation(
        summary = "특정 설문 답변 조회",
        description = "특정 설문 양식의 답변을 조회합니다.",
        responses = [
            ApiResponse(responseCode = "200", description = "설문 답변이 성공적으로 반환되었습니다."),
            ApiResponse(
                responseCode = "404",
                description = "해당 설문 답변을 찾을 수 없습니다.",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))],
            ),
        ],
    )
    @GetMapping("/{surveyFormId}/answers")
    fun getSurveyAnswers(
        @PathVariable(name = "surveyFormId", required = true)
        @Parameter(description = "조회할 설문 양식 ID", required = true)
        surveyFormId: String,
    ): SurveyFormAnswerResponse =
        run { surveyAnswerService.getSurveyAnswerBySurveyFormId(surveyFormId = surveyFormId) }
            .let { objectMapperUtil.convertClass(value = it, clazz = SurveyFormAnswerResponse::class.java) }

    @Operation(
        summary = "설문 제목으로 답변 조회",
        description = "설문 제목을 기반으로 설문 답변을 조회합니다.",
        responses = [
            ApiResponse(responseCode = "200", description = "설문 답변이 성공적으로 반환되었습니다."),
            ApiResponse(
                responseCode = "404",
                description = "해당 설문 제목을 가진 답변을 찾을 수 없습니다.",
                content = [Content(schema = Schema(implementation = CommonErrorResponse::class))],
            ),
        ],
    )
    @GetMapping("/answers")
    fun getSurveyAnswersBySurveyTitle(
        @RequestParam(name = "surveyTitle", required = true)
        @Parameter(description = "설문 제목", required = true)
        surveyTitle: String,
    ) = let { surveyAnswerService.getSurveyAnswerByTitle(surveyTitle) }

    @Operation(
        summary = "설문 응답 항목 및 값 검색",
        description = "응답 항목의 이름과 값을 기반으로 설문 답변을 검색할 수 있는 기능입니다. (미구현)",
    )
    @GetMapping("/answers/search")
    fun searchSurveyAnswers(
        @RequestParam(name = "surveyItemName", required = false) surveyItemName: String?,
        @RequestParam(name = "surveyItemAnswer", required = false) surveyItemAnswer: String?,
    ): List<SurveyAnswerSearchResponse> =
        run { surveyAnswerService.getSurveyAnswerSearchBy(surveyItemName, surveyItemAnswer) }
            .map { objectMapperUtil.convertClass(value = it, clazz = SurveyAnswerSearchResponse::class.java) }
}
