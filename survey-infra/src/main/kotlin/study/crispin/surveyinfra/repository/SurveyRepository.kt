package study.crispin.surveyinfra.repository

import java.util.UUID
import study.crispin.surveyinfra.repository.entity.SurveyEntity

internal interface SurveyRepository {
    fun save(entity: SurveyEntity): SurveyEntity
    fun findById(id: UUID): SurveyEntity?
}
