package com.ic.surveydata.answer

import com.ic.surveydata.answer.dto.SurveyAnswerDto
import com.ic.surveydata.answer.dto.SurveyAnswerSearchDto
import com.ic.surveydata.answer.dto.SurveyFormAnswerDto
import com.ic.surveydata.answer.dto.toDto
import com.ic.surveydata.answer.dto.toDtoSurveyAnswerDto
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

    // TODO - 데이터 가져오는 구조를 ... ㅜㅜ ! 다시 고려가 필요 하다 (first 로 가쟈오는 부분 수정이 필요 .. !
    // SurveyFormId 에 해당 하는 Answer 들을 전부 조회 후 Response 를 해 줌 ... !
    fun findSurveyAnswersBySurveyFormId(surveyFormId: String): SurveyFormAnswerDto {
        return surveyItemRepository.findBySurveyFormId(surveyFormId).map { it.toDto() }.first()
    }

    fun findSurveyAnswerBySurveyTitle(surveyTitle: String): List<SurveyFormAnswerDto> =
        surveyItemRepository.findBySurveyTitle(surveyTitle).map { it.toDto() }

    fun findSurveyAnswerBy(
        surveyItemName: String?,
        surveyItemAnswer: String?,
    ): List<SurveyAnswerSearchDto> =
        surveyItemRepository.findBySurveyItemNameAndAnswer(surveyItemName, surveyItemAnswer)
            .let { it.map { entity -> entity.toDtoSurveyAnswerDto() } }
}
