package study.crispin.surveyinfra.repository

import study.crispin.surveyinfra.adaptor.dto.SurveyItemDto
import study.crispin.surveyinfra.repository.entity.SurveyItemEntity

internal interface SurveyItemRepository {
    fun saveAll(entities: List<SurveyItemEntity>): List<SurveyItemDto>
}
