package com.ic.surveydata.service

import com.ic.surveydata.entity.form.SurveyFormEntity
import com.ic.surveydata.repository.SurveyFormRepository
import com.ic.surveydata.service.dto.SurveyCreateRequestDto
import com.ic.surveydata.service.dto.toEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SurveyFormDataHandler(
    private val surveyFormRepository: SurveyFormRepository,
) {
    // TODO - 로직 정리 및 트랜잭션 처리가 필요하다
    fun createNewSurveyForm(surveyForm: SurveyCreateRequestDto): SurveyFormEntity {
        val newestVersionFormOrDefault =
            surveyFormRepository.findLatestVersionFormBy(title = surveyForm.title)?.plus(1) ?: ZERO_SURVEY_FORM_VERSION

        return surveyFormRepository.save(surveyForm.toEntity(version = newestVersionFormOrDefault))
    }

    companion object {
        val logger = LoggerFactory.getLogger(SurveyFormDataHandler::class.java)
        const val ZERO_SURVEY_FORM_VERSION: Int = 0
    }
}
