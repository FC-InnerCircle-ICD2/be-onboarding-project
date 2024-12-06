package com.ic.surveyapi.form.controller

import com.ic.surveyapi.exception.controller.CommonErrorResponse
import com.ic.surveyapi.form.controller.dto.SurveyFormCreateRequest
import com.ic.surveyapi.form.controller.dto.SurveyFormCreateResponse
import com.ic.surveyapi.form.controller.dto.SurveyFormResponse
import com.ic.surveyapi.form.service.SurveyFormService
import com.ic.surveyapi.form.service.dto.SurveyFormCreateRequestDto
import com.ic.surveyapi.util.ObjectMapperUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@Tag(name = "Survey Form", description = "설문 양식 관리 API")
@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_SURVEY_PREFIX)
class SurveyFormController(
    private val surveyFormService: SurveyFormService,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    @Operation(
        summary = "설문 양식 생성",
        description = "새로운 설문 양식을 생성합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "설문 양식이 성공적으로 생성되었습니다.",
                content = [Content(schema = Schema(implementation = SurveyFormCreateResponse::class))],
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청입니다.",
                content = [Content(schema = Schema(implementation = CommonErrorResponse::class))],
            ),
        ],
    )
    @PostMapping
    fun createSurveyForms(
        @RequestBody
        @Parameter(description = "생성할 설문 양식 정보", required = true)
        surveyForm: SurveyFormCreateRequest,
    ): SurveyFormCreateResponse =
        run { objectMapperUtil.convertClass(value = surveyForm, clazz = SurveyFormCreateRequestDto::class.java) }
            .let { surveyFormService.createSurveyForm(surveyForm = it) }
            .let { SurveyFormCreateResponse.of(it) }

    @Operation(
        summary = "설문 양식 조회",
        description = "특정 설문 양식의 상세 정보를 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "설문 양식 정보가 성공적으로 반환되었습니다.",
                content = [Content(schema = Schema(implementation = SurveyFormResponse::class))],
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 설문 양식을 찾을 수 없습니다.",
                content = [Content(schema = Schema(implementation = CommonErrorResponse::class))],
            ),
        ],
    )
    @GetMapping("/{surveyFormId}")
    fun getSurveyFormHistories(
        @PathVariable(value = "surveyFormId", required = true)
        @Parameter(description = "조회할 설문 양식의 ID", required = true)
        surveyFormId: String,
    ): SurveyFormResponse =
        run { surveyFormService.getSurveyFormBySurveyFormId(surveyFormId = surveyFormId) }
            .let { objectMapperUtil.convertClass(value = it, clazz = SurveyFormResponse::class.java) }
}
