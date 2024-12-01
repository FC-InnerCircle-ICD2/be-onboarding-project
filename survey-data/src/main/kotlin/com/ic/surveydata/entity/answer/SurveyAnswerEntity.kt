package com.ic.surveydata.entity.answer

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "survey_answer")
data class SurveyAnswerEntity(
    @Id
    val id: String,
)