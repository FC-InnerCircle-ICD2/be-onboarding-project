package com.ic.surveydata.entity.form

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import survey.type.ItemType
import java.time.LocalDateTime

@Entity
@Table(name = "survey_item")
data class SurveyItemEntity(
    @Id
    @Column(name = "id", unique = true, nullable = false)
    val id: String = "",
    @ManyToOne
    @JoinColumn(name = "survey_form_id")
    val surveyFormEntity: SurveyFormEntity? = null,
    @OneToMany(mappedBy = "surveyItemEntity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val surveyOptions: MutableList<SurveyOptionEntity> = mutableListOf(),
    @Column(name = "name", nullable = false, unique = false)
    val name: String,
    @Column(name = "is_required", nullable = false, unique = false)
    val isRequired: Boolean,
    @Column(name = "description", nullable = false, unique = false)
    val description: String,
    @Column(nullable = false, unique = false)
    val type: ItemType,
    // TODO - 혹시 kotlinx.datetime 을 사용할 수 있는 방법을 찾아보자 ... !
    @Column(name = "created_at", nullable = false, unique = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @OneToMany(mappedBy = "surveyItemEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val options: List<SurveyOptionEntity> = mutableListOf(),
)
