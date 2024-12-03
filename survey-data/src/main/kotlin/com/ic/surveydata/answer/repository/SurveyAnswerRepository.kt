package com.ic.surveydata.answer.repository

import com.ic.surveydata.answer.entity.SurveyAnswerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyAnswerRepository : JpaRepository<SurveyAnswerEntity, String>
