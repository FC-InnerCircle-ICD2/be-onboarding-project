package com.ic.surveyapi.form.controller

import com.ic.surveyapi.form.controller.dto.SurveyFormCreateRequest
import com.ic.surveyapi.form.controller.dto.SurveyFormCreateResponse
import com.ic.surveyapi.form.service.SurveyFormService
import com.ic.surveyapi.form.service.dto.SurveyFormCreateRequestDto
import com.ic.surveyapi.util.ObjectMapperUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    ): SurveyFormCreateResponse =
        run { objectMapperUtil.convertClass(value = surveyForm, clazz = SurveyFormCreateRequestDto::class.java) }
            .let { surveyFormService.createSurveyForm(surveyForm = it) }
            .let { SurveyFormCreateResponse.of(it) }

    @GetMapping("/{surveyTitle}")
    fun getSurveyFormHistories(
        @PathVariable(value = "surveyTitle", required = true) surveyTitle: String,
    ) {
    }
}
