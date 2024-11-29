package com.ic.surveydata.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "survey_option")
class SurveyOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id = 0

    @Column(nullable = false, unique = true)
    val itemUuid: String = ""

    @Column(nullable = false, unique = false)
    val name: String = ""
}
