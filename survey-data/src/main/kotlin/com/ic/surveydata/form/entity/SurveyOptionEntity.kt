package com.ic.surveydata.form.entity

import com.ic.surveydata.BaseTimeEntity
import com.ic.surveydata.form.dto.SurveyFormCreateRequestDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import survey.util.UuidGeneratorUtil

@Entity
@Table(name = "survey_option")
class SurveyOptionEntity(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: String,
    @Column(nullable = false, unique = false)
    val name: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "survey_item_id", referencedColumnName = "id")
    var surveyItemEntity: SurveyItemEntity? = null,
) : BaseTimeEntity() {
    override fun toString(): String {
        return "SurveyOptionEntity(" +
                "id='$id', " +
                "name='$name', " +
                "surveyItemId=${surveyItemEntity?.id}" +
                ")"
    }

    companion object {
        fun of(dto: SurveyFormCreateRequestDto.SurveyOption): SurveyOptionEntity =
            SurveyOptionEntity(
                id = UuidGeneratorUtil.generateUuid(),
                name = dto.name,
            )
    }
}
