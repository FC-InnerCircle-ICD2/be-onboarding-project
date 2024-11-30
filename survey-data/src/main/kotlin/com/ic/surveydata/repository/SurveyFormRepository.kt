package com.ic.surveydata.repository

import com.ic.surveydata.entity.SurveyFormEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SurveyFormRepository : JpaRepository<SurveyFormEntity, String> {
    @Query(
        "SELECT t.version " +
            "FROM SurveyFormEntity t " +
            "WHERE t.title = :title " +
            "AND t.version = (SELECT MAX(sub.version) FROM SurveyFormEntity sub WHERE sub.title = :title)",
    )
    fun findLatestVersionFormBy(title: String): Int?
}
