package com.ic.surveyapi.answer.service

import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveyapi.util.ObjectMapperUtil
import com.ic.surveydata.answer.SurveyAnswerDataHandler
import org.springframework.stereotype.Service

@Service
class SurveyAnswerService(
    private val surveyAnswerDataHandler: SurveyAnswerDataHandler,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    fun submitSurveyAnswer(
        surveyFormId: String,
        surveyAnswer: SurveyAnswerDto,
    ) {
        surveyAnswerDataHandler.insertSurveyAnswers(
            surveyFormId = surveyFormId,
            surveyAnswerDto =
                objectMapperUtil.convertClass(
                    value = surveyAnswer,
                    clazz = com.ic.surveydata.answer.dto.SurveyAnswerDto::class.java,
                ),
        )
    }
}
