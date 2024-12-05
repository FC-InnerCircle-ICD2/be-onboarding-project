package com.ic.surveydata.form

import com.ic.surveydata.form.dto.SurveyFormCreateRequestDto
import com.ic.surveydata.form.dto.SurveyFormDto
import com.ic.surveydata.form.dto.toEntity
import com.ic.surveydata.form.entity.SurveyFormEntity
import com.ic.surveydata.form.repositry.SurveyFormRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SurveyFormDataHandler(
    private val surveyFormRepository: SurveyFormRepository,
) {
    // TODO - DTO Response 통일 필요
    fun insertSurveyForm(surveyForm: SurveyFormCreateRequestDto): SurveyFormEntity {
        val newestVersionFormOrDefault =
            surveyFormRepository.findLatestVersionFormBy(title = surveyForm.title)?.plus(1) ?: ZERO_SURVEY_FORM_VERSION

        return surveyFormRepository.save(surveyForm.toEntity(version = newestVersionFormOrDefault))
    }

    fun findSurveyFormById(id: String): SurveyFormDto =
        SurveyFormDto.of(surveyFormRepository.findById(id).orElseThrow())

    fun findSurveyFormByIdOrNull(id: String): SurveyFormEntity =
        surveyFormRepository.findById(id).orElseThrow()

    companion object {
        val logger = LoggerFactory.getLogger(SurveyFormDataHandler::class.java)
        const val ZERO_SURVEY_FORM_VERSION: Int = 0
    }
}
