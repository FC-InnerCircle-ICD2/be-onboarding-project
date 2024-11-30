package study.crispin.surveyinfra.repository

import study.crispin.surveyinfra.repository.entity.SurveyEntity

internal interface SurveyRepository {
    fun save(entity: SurveyEntity): SurveyEntity
}
