package com.ic.surveydata.repository

import com.ic.surveydata.entity.SurveyForm
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyRepository : JpaRepository<SurveyForm, Long>
