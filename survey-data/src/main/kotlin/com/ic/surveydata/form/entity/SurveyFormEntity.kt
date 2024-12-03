package com.ic.surveydata.form.entity

import com.ic.surveydata.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

// TODO - toString() 순환 참조 문제 해결 필요 StackOverFlow 에러가 발생
@Entity
@Table(name = "survey_form")
data class SurveyFormEntity(
    // TODO - 코틀린에서 엔티티 생성 베스트 프랙티스를 확인이 필요 하다. DB 컬럼 순서 맞추기 !
    @Id
    @Column(name = "id", unique = true, nullable = false)
    val id: String,
    @Column(name = "title", unique = false, nullable = true)
    val title: String,
    @Column(name = "description")
    val description: String,
    @OneToMany(mappedBy = "surveyFormEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val surveyItems: MutableList<SurveyItemEntity> = mutableListOf(),
    @Column(name = "version", nullable = false, unique = false)
    val version: Int,
) : BaseTimeEntity() {
    fun addSurveyItems(items: List<SurveyItemEntity>) {
        surveyItems.addAll(items)
        items.forEach { surveyItemEntity -> surveyItemEntity.surveyFormEntity = this }
    }

    // TODO - 해당 이유를 못찾았다. 원인을 파악하여야 한다. 지우면 널포인터 발생 .. ㅜㅜ ! 원인 파악이 필요 !!
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return ""
    }
}
