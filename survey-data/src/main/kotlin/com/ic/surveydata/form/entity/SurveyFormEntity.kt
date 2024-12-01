package com.ic.surveydata.form.entity

import com.ic.surveydata.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Version

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
    var surveyItems: MutableList<SurveyItemEntity> = mutableListOf(),
    @Version
    @Column(name = "version", nullable = false, unique = false)
    val version: Int,
): BaseTimeEntity()
