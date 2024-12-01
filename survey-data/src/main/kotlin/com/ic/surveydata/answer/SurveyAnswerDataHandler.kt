package com.ic.surveydata.answer

import com.ic.surveydata.answer.dto.SurveyAnswerDto
import com.ic.surveydata.answer.dto.toEntity
import com.ic.surveydata.answer.repository.SurveyAnswerRepository
import com.ic.surveydata.form.repositry.SurveyFormRepository
import org.springframework.stereotype.Component

@Component
class SurveyAnswerDataHandler(
    private val surveyAnswerRepository: SurveyAnswerRepository,
    private val surveyFormRepository: SurveyFormRepository,
) {
    fun insertSurveyAnswers(
        surveyFormId: String,
        surveyAnswerDto: SurveyAnswerDto,
    ) {
        val surveyFormEntity = surveyFormRepository.findById(surveyFormId).get()
        val surveyAnswerEntities = surveyAnswerDto.toEntity(surveyFormEntity = surveyFormEntity)
        surveyAnswerRepository.saveAll(surveyAnswerEntities)
    }
}
