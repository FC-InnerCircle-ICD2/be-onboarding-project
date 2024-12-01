package com.ic.surveydata.component

import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.repositry.SurveyFormRepository
import com.ic.surveydata.component.dto.SurveyCreateRequestDto
import com.ic.surveydata.component.dto.toEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SurveyFormDataHandler(
    private val surveyFormRepository: SurveyFormRepository,
) {
    // TODO - 로직 정리 및 트랜잭션 처리가 필요하다
    fun insertSurveyForm(surveyForm: SurveyCreateRequestDto): SurveyFormEntity {
        val newestVersionFormOrDefault =
            surveyFormRepository.findLatestVersionFormBy(title = surveyForm.title)?.plus(1) ?: ZERO_SURVEY_FORM_VERSION

        return surveyFormRepository.save(surveyForm.toEntity(version = newestVersionFormOrDefault))
    }

    companion object {
        val logger = LoggerFactory.getLogger(SurveyFormDataHandler::class.java)
        const val ZERO_SURVEY_FORM_VERSION: Int = 0
    }
}
