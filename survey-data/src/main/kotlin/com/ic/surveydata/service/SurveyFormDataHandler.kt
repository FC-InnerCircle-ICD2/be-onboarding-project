package com.ic.surveydata.service

import com.ic.surveydata.entity.SurveyForm
import com.ic.surveydata.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class SurveyFormDataHandler(
    private val surveyRepository: SurveyRepository,
) {
    fun createForm(surveyForm: SurveyForm): SurveyForm {
        return surveyRepository.save(surveyForm)
    }
}