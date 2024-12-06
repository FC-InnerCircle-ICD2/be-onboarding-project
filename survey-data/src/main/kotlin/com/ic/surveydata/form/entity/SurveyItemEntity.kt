package com.ic.surveydata.form.entity

import com.ic.surveydata.BaseTimeEntity
import com.ic.surveydata.answer.entity.SurveyAnswerEntity
import com.ic.surveydata.form.dto.SurveyFormCreateRequestDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import survey.type.ItemType
import survey.util.UuidGeneratorUtil

@Entity
@Table(name = "survey_item")
class SurveyItemEntity(
    @Id
    @Column(name = "id", unique = true, nullable = false)
    val id: String,
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "survey_form_id", referencedColumnName = "id", nullable = false)
    var surveyFormEntity: SurveyFormEntity? = null,
    @OneToMany(mappedBy = "surveyItemEntity", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    val surveyOptions: MutableSet<SurveyOptionEntity> = mutableSetOf(),
    @OneToMany(mappedBy = "surveyItemEntity", cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    val surveyAnswers: MutableList<SurveyAnswerEntity> = mutableListOf(),
    @Column(name = "name", nullable = false, unique = false)
    val name: String,
    @Column(name = "is_required", nullable = false, unique = false)
    val isRequired: Boolean,
    @Column(name = "description", nullable = false, unique = false)
    val description: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = false)
    val type: ItemType,
) : BaseTimeEntity() {
    fun addSurveyOption(surveyOption: List<SurveyOptionEntity>) {
        surveyOptions.addAll(surveyOption)
        surveyOption.forEach { it.surveyItemEntity = this }
    }

    override fun toString(): String {
        return "SurveyItemEntity(" +
            "id='$id', " +
            "name='$name', " +
            "isRequired=$isRequired, " +
            "description='$description', " +
            "type=$type, " +
            "surveyFormEntityId=${surveyFormEntity?.id}, " + // surveyFormEntity의 ID만 출력
            "surveyOptions=${surveyOptions.map { it.toString() }}, " + // surveyOptions의 ID 목록만 출력
            "surveyAnswers=${surveyAnswers.map { it.toString() }}" + // surveyAnswers의 ID 목록만 출력
            ")"
    }

    companion object {
        fun of(dto: SurveyFormCreateRequestDto.SurveyItem): SurveyItemEntity {
            return SurveyItemEntity(
                id = UuidGeneratorUtil.generateUuid(),
                name = dto.name,
                type = dto.type,
                description = dto.description,
                isRequired = dto.isRequired,
            )
        }
    }
}
