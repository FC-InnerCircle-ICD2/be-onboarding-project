package com.ic.surveyapi.form.controller

import com.ic.surveyapi.form.controller.dto.SurveyFormCreateRequest
import com.ic.surveyapi.form.controller.dto.SurveyFormCreateResponse
import com.ic.surveyapi.form.service.SurveyFormService
import com.ic.surveyapi.form.service.dto.SurveyFormCreateRequestDto
import com.ic.surveyapi.util.ObjectMapperUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix
import survey.extension.LocalDateTimeExtension.toResponseDateTimeFormat

@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_SURVEY_PREFIX)
class SurveyFormController(
    private val surveyFormService: SurveyFormService,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    @PostMapping
    fun createSurveyForms(
        @RequestBody surveyForm: SurveyFormCreateRequest,
    ): SurveyFormCreateResponse {
        val surveyEntity =
            surveyFormService.createSurveyForm(
                surveyForm = objectMapperUtil.convertClass(value = surveyForm, clazz = SurveyFormCreateRequestDto::class.java),
            )
        return SurveyFormCreateResponse(
            id = surveyEntity.id,
            title = surveyEntity.title,
            createdDateTime = surveyEntity.createdAt.toResponseDateTimeFormat(),
        )
    }
}
