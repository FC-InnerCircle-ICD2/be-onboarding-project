package com.ic.surveyapi.service

import com.ic.surveydata.entity.SurveyForm
import com.ic.surveydata.service.SurveyFormDataHandler
import org.springframework.stereotype.Service

@Service
class SurveyService(
    private val surveyFormDataHandler: SurveyFormDataHandler,
) {
    fun createSurvey(surveyForm: SurveyForm): SurveyForm {
        surveyFormDataHandler.createForm(surveyForm = surveyForm)
//       surveyRepository.save(survey)
        return SurveyForm()
    }
}
