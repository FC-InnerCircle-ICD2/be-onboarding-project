package com.ic.surveydata.form.repositry

import com.ic.surveydata.form.entity.SurveyFormEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

// TODO SurveyFormRepository 접근 제한자를 조정해보자 ! 아무데서나 접근할 수 없도록 ... !!
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
