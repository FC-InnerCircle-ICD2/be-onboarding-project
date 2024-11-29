package com.ic.surveydata.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import survey.type.ItemType
import java.time.LocalDateTime

@Entity
@Table(name = "survey_item")
class SurveyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "uuid", nullable = false, unique = true)
    val uuid: String = ""

    @Column(name = "form_uuid", nullable = false, unique = true)
    val formUuid: String = ""

    @Column(name = "name", nullable = false, unique = false)
    val name: String = ""

    @Column(name = "is_required", nullable = false, unique = false)
    val isRequired: Boolean = true

    @Column(name = "description", nullable = false, unique = true)
    val description: String = ""

    @Column(nullable = false, unique = true)
    val type: ItemType = ItemType.SHORT_ANSWER

    // TODO - 혹시 kotlinx.datetime 을 사용할 수 있는 방법을 찾아보자 ... !
    @Column(name = "created_at", nullable = false, unique = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}
