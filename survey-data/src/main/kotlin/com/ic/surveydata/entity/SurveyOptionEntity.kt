package com.ic.surveydata.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "survey_option")
data class SurveyOptionEntity(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: String,
    @Column(nullable = false, unique = false)
    val name: String,
    @ManyToOne
    @JoinColumn(name = "survey_item_id")
    val surveyItemEntity: SurveyItemEntity? = null,
    @Column(name = "created_at", nullable = false, unique = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
