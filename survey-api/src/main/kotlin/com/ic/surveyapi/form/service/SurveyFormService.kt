package com.ic.surveyapi.form.service

import com.ic.surveyapi.form.service.dto.SurveyFormCreateRequestDto
import com.ic.surveyapi.util.ObjectMapperUtil
import com.ic.surveydata.form.SurveyFormDataHandler
import com.ic.surveydata.form.dto.SurveyFormDto
import com.ic.surveydata.form.entity.SurveyFormEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SurveyFormService(
    private val surveyFormDataHandler: SurveyFormDataHandler,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    @Transactional(
        readOnly = false,
        rollbackFor = [Exception::class],
    )
    fun createSurveyForm(surveyForm: SurveyFormCreateRequestDto): SurveyFormEntity =
        run { objectMapperUtil.convertClass(value = surveyForm, clazz = com.ic.surveydata.form.dto.SurveyFormCreateRequestDto::class.java) }
            .let { surveyFormDataHandler.insertSurveyForm(surveyForm = it) }

    @Transactional(readOnly = true)
    fun getSurveyFormBySurveyFormId(surveyFormId: String): SurveyFormDto =
        run {
            surveyFormDataHandler.findSurveyFormById(id = surveyFormId)
        }
}
