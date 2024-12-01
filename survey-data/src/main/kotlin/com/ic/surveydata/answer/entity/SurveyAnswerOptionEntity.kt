package com.ic.surveydata.answer.entity

import com.ic.surveydata.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "survey_answer_option")
data class SurveyAnswerOptionEntity(
    @Id
    val id: String,
    @Column(name = "answer", nullable = true, unique = false)
    val answer: String,
) : BaseTimeEntity()
