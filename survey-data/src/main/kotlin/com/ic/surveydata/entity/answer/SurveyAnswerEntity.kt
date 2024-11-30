package com.ic.surveydata.entity.answer

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "survey_answers")
data class SurveyAnswerEntity(
    @Id
    val id: String,

)