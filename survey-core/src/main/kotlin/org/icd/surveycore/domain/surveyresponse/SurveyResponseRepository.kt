package org.icd.surveycore.domain.surveyresponse

import org.springframework.data.jpa.repository.JpaRepository

interface SurveyResponseRepository : JpaRepository<SurveyResponse, Long> {
}