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
    // TODO : DTO 어디서 매핑 할 건지 결정이 필요
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
