package com.ic.surveyapi.form.service

import com.ic.surveyapi.form.service.dto.SurveyFormCreateRequestDto
import com.ic.surveyapi.util.ObjectMapperUtil
import com.ic.surveydata.form.SurveyFormDataHandler
import com.ic.surveydata.form.entity.SurveyFormEntity
import org.springframework.stereotype.Service

@Service
class SurveyFormService(
    private val surveyFormDataHandler: SurveyFormDataHandler,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    // TODO : DTO 어디서 매핑 할 건지 결정이 필요
    fun createSurveyForm(surveyForm: SurveyFormCreateRequestDto): SurveyFormEntity =
        run { objectMapperUtil.convertClass(value = surveyForm, clazz = com.ic.surveydata.form.dto.SurveyFormCreateRequestDto::class.java) }
        .let { surveyFormDataHandler.insertSurveyForm(surveyForm = it) }

}
