package com.ic.surveydata.form.entity

import com.ic.surveydata.BaseTimeEntity
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

@Entity
@Table(name = "survey_item")
data class SurveyItemEntity(
    @Id
    @Column(name = "id", unique = true, nullable = false)
    val id: String,
    @ManyToOne
    @JoinColumn(name = "survey_form_id")
    var surveyFormEntity: SurveyFormEntity? = null,
    @OneToMany(mappedBy = "surveyItemEntity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val surveyOptions: MutableSet<SurveyOptionEntity> = mutableSetOf(),
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

    // TODO - 참조 순환 에러 문제 ... ㅜㅜㅜ 해결 해야한다 !
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return ""
    }
}
