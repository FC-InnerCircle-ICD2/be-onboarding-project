package com.ic.surveydata.form

import com.ic.surveydata.form.dto.SurveyFormCreateRequestDto
import com.ic.surveydata.form.dto.toEntity
import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.repositry.SurveyFormRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class SurveyFormDataHandler(
    internal val surveyFormRepository: SurveyFormRepository,
) {
    // TODO - 로직 정리 및 트랜잭션 처리가 필요하다
    fun insertSurveyForm(surveyForm: SurveyFormCreateRequestDto): SurveyFormEntity {
        val newestVersionFormOrDefault =
            surveyFormRepository.findLatestVersionFormBy(title = surveyForm.title)?.plus(1) ?: ZERO_SURVEY_FORM_VERSION

        return surveyFormRepository.save(surveyForm.toEntity(version = newestVersionFormOrDefault))
    }

    fun findSurveyFormByIdOrNull(id: String): SurveyFormEntity? = surveyFormRepository.findByIdOrNull(id)

    companion object {
        val logger = LoggerFactory.getLogger(SurveyFormDataHandler::class.java)
        const val ZERO_SURVEY_FORM_VERSION: Int = 0
    }
}
