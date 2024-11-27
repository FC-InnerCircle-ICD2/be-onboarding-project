package study.crispin.surveyinfra.repository

import study.crispin.surveyinfra.adaptor.dto.SurveyDto
import study.crispin.surveyinfra.repository.entity.SurveyEntity

internal interface SurveyRepository {
    fun save(entity: SurveyEntity): SurveyDto
}
