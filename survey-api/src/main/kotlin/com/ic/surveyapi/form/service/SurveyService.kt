package com.ic.surveyapi.form.service

import com.ic.surveyapi.form.service.dto.SurveyCreateRequestDto
import com.ic.surveyapi.util.ObjectMapperUtil
import com.ic.surveydata.entity.SurveyFormEntity
import com.ic.surveydata.service.SurveyFormDataHandler
import org.springframework.stereotype.Service

@Service
class SurveyService(
    private val surveyFormDataHandler: SurveyFormDataHandler,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    fun createSurvey(surveyForm: SurveyCreateRequestDto): SurveyFormEntity {
        return surveyFormDataHandler.createNewSurveyForm(
            surveyForm =
                objectMapperUtil.convertClass(
                    value = surveyForm,
                    clazz = com.ic.surveydata.service.dto.SurveyCreateRequestDto::class.java,
                ),
        )
    }
}
