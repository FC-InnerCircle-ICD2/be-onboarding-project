package com.ic.surveydata.form.entity

import com.ic.surveydata.BaseTimeEntity
import com.ic.surveydata.form.dto.SurveyFormCreateRequestDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import survey.util.UuidGeneratorUtil

// TODO - toString() 순환 참조 문제 해결 필요 StackOverFlow 에러가 발생
@Entity
@Table(name = "survey_form")
class SurveyFormEntity(
    // TODO - 코틀린에서 엔티티 생성 베스트 프랙티스를 확인이 필요 하다. DB 컬럼 순서 맞추기 !
    @Id
    @Column(name = "id", unique = true, nullable = false)
    val id: String,
    @Column(name = "title", unique = false, nullable = true)
    val title: String,
    @Column(name = "description")
    val description: String,
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "surveyFormEntity", cascade = [CascadeType.ALL])
    var surveyItems: MutableList<SurveyItemEntity> = mutableListOf(),
    @Column(name = "version", nullable = false, unique = false)
    val version: Int,
) : BaseTimeEntity() {
    fun addSurveyItems(items: List<SurveyItemEntity>) {
        surveyItems.addAll(items)
        items.forEach { surveyItemEntity -> surveyItemEntity.surveyFormEntity = this }
    }

    override fun toString(): String {
        return "SurveyFormEntity(id='$id', title='$title', description='$description', " +
            "version=$version, surveyItems=${surveyItems.joinToString(separator = ", ") { it.toString() }})"
    }

    companion object {
        fun of(
            dto: SurveyFormCreateRequestDto,
            version: Int,
        ): SurveyFormEntity {
            return SurveyFormEntity(
                id = UuidGeneratorUtil.generateUuid(),
                title = dto.title,
                description = dto.description,
                version = version,
            )
        }
    }
}
