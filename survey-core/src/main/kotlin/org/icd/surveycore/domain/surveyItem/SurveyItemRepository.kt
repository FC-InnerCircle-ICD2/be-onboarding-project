package org.icd.surveycore.domain.surveyItem

import org.springframework.data.jpa.repository.JpaRepository

interface SurveyItemRepository : JpaRepository<SurveyItem, Long> {
}