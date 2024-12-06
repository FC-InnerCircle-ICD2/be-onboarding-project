package com.ic.surveydata.answer.entity

import com.ic.surveydata.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

// TODO survey_answer_historu 같은 혹시 네이밍은 어떨까 .. ?!
@Entity
@Table(name = "survey_answer_option")
class SurveyAnswerOptionEntity(
    @Id
    val id: String,
    @Column(name = "answer", nullable = true, unique = false)
    val answer: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_answer_id", referencedColumnName = "id", nullable = false)
    val surveyAnswerEntity: SurveyAnswerEntity? = null,
) : BaseTimeEntity() {
    override fun toString(): String {
        return "SurveyAnswerOptionEntity(" +
            "id='$id', " +
            "answer='$answer', " +
            "surveyAnswerId=${surveyAnswerEntity?.id}" + // SurveyAnswerEntity의 ID만 출력
            ")"
    }
}
