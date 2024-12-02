package com.ic.surveyapi.answer.service

import com.ic.surveyapi.answer.service.dto.SurveyAnswerDto
import com.ic.surveyapi.util.InputParameterValidator.validateOrThrow
import com.ic.surveyapi.util.ObjectMapperUtil
import com.ic.surveydata.answer.SurveyAnswerDataHandler
import com.ic.surveydata.form.SurveyFormDataHandler
import org.springframework.stereotype.Service

@Service
class SurveyAnswerService(
    private val surveyAnswerDataHandler: SurveyAnswerDataHandler,
    private val surveyFormDataHandler: SurveyFormDataHandler,
    private val objectMapperUtil: ObjectMapperUtil,
) {
    fun submitSurveyAnswer(
        surveyFormId: String,
        surveyAnswer: SurveyAnswerDto,
    ) {
        // TODO - Entity 가 아닌 DTO 로 가져오자 .. !
        val surveyFormEntity =
            surveyFormDataHandler.findSurveyFormByIdOrNull(surveyFormId)
                ?: throw IllegalArgumentException("SurveyForm with $surveyFormId does not exist")

        surveyAnswer.validateOrThrow(surveyFormEntity = surveyFormEntity)
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
