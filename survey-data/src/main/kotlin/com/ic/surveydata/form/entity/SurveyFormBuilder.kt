package com.ic.surveydata.form.entity

import survey.util.UuidGeneratorUtil


class SurveyFormEntityBuilder {
    private var id: String = UuidGeneratorUtil.generateUuid()
    private var title: String = ""
    private var description: String = ""
    private var version: Int = 1
    private val surveyItems: MutableList<SurveyItemEntity> = mutableListOf()

    fun title(title: String) = apply { this.title = title }
    fun description(description: String) = apply { this.description = description }
    fun version(version: Int) = apply { this.version = version }
    fun addSurveyItem(surveyItem: List<SurveyItemEntity>) = apply { surveyItems.addAll(surveyItem) }


    fun build(): SurveyFormEntity {
        val formEntity = SurveyFormEntity(id, title, description, surveyItems, version)
        surveyItems.forEach { it.surveyFormEntity = formEntity }
        return formEntity
    }
}