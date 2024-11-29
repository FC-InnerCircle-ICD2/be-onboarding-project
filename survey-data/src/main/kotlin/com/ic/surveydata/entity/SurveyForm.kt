package com.ic.surveydata.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "survey_form")
class SurveyForm {
    // TODO - 코틀린에서 엔티티 생성 베스트 프랙티스를 확인이 필요 하다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "uuid", unique = false, nullable = true)
    var uuid: String = ""

    @Column(name = "title", unique = false, nullable = true)
    val title: String = ""

    @Column(name = "description")
    val description: String = ""

    @Column(name = "version", nullable = false, unique = false)
    val version: Int = 0

    @Column(name = "created_at", nullable = false, unique = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}
