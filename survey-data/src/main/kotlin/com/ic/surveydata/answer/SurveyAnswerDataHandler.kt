package com.ic.surveydata.answer

import com.ic.surveydata.answer.dto.SurveyAnswerDto
import com.ic.surveydata.answer.dto.SurveyFormAnswerDto
import com.ic.surveydata.answer.dto.toDto
import com.ic.surveydata.answer.dto.toEntity
import com.ic.surveydata.answer.repository.SurveyAnswerRepository
import com.ic.surveydata.answer.repository.SurveyItemRepository
import com.ic.surveydata.form.repositry.SurveyFormRepository
import org.springframework.stereotype.Component

@Component
class SurveyAnswerDataHandler(
    private val surveyAnswerRepository: SurveyAnswerRepository,
    private val surveyFormRepository: SurveyFormRepository,
    private val surveyItemRepository: SurveyItemRepository,
) {
    fun insertSurveyAnswers(
        surveyFormId: String,
        surveyAnswerDto: SurveyAnswerDto,
    ) {
        val surveyFormEntity = surveyFormRepository.findById(surveyFormId).get()
        val surveyAnswerEntities = surveyAnswerDto.toEntity(surveyFormEntity = surveyFormEntity)
        surveyAnswerRepository.saveAll(surveyAnswerEntities)
    }

    // SurveyFormId 에 해당 하는 Answer 들을 전부 조회 후 Response 를 해 줌 ... !
    fun findSurveyAnswersBySurveyFormId(surveyFormId: String): List<SurveyFormAnswerDto> {
        return surveyItemRepository.findBySurveyFormId(surveyFormId).map { it.toDto() }
    }
}
