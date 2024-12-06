package org.icd.surveycore.domain.survey

import org.springframework.data.jpa.repository.JpaRepository

interface SurveyRepository : JpaRepository<Survey, Long> {

}