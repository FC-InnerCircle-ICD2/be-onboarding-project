package com.ic.surveyapi.form.controller

import com.ic.surveyapi.form.controller.dto.SurveyCreateRequest
import com.ic.surveyapi.form.controller.dto.SurveyCreateResponse
import com.ic.surveyapi.form.service.SurveyService
import com.ic.surveyapi.form.service.dto.SurveyCreateRequestDto
import com.ic.surveyapi.util.ObjectMapperUtil
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import survey.common.ApiEndpointVersionPrefix

@RestController
@RequestMapping(ApiEndpointVersionPrefix.V1_API_PREFIX)
class SurveyController(
    private val surveyService: SurveyService,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    @PostMapping("/surveys")
    fun createSurveyForms(
        @RequestBody surveyForm: SurveyCreateRequest,
    ): SurveyCreateResponse {
        val surveyEntity =
            surveyService.createSurvey(
                surveyForm = objectMapperUtil.convertClass(value = surveyForm, clazz = SurveyCreateRequestDto::class.java),
            )
        return SurveyCreateResponse(
            uuid = surveyEntity.id,
            title = surveyEntity.title,
            createdDateTime = surveyEntity.createdAt.toKotlinLocalDateTime(),
        )
    }
}
