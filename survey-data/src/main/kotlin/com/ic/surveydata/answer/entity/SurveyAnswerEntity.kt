package com.ic.surveydata.answer.entity

import com.ic.surveydata.BaseTimeEntity
import com.ic.surveydata.form.entity.SurveyItemEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "survey_answer")
data class SurveyAnswerEntity(
    @Id
    val id: String,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_item_id", referencedColumnName = "id", unique = false)
    val survenItemEntity: SurveyItemEntity? = null,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_option_id", referencedColumnName = "id")
    val surveyAnswerOptions: MutableList<SurveyAnswerOptionEntity> = mutableListOf(),
) : BaseTimeEntity()
