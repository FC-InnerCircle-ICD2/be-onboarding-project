package org.icd.surveycore.domain.surveyresponse

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.icd.surveycore.domain.survey.Survey
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@Entity
@Table(name = "survey_response")
@EntityListeners(AuditingEntityListener::class)
class SurveyResponse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Comment("설문조사 아이디")
    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    val survey: Survey,
    @Comment("중복 제출 방지용 유니크 아이디")
    val uuid: String,
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "surveyResponse", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<SurveyResponseItem> = mutableListOf(),
    @CreatedDate
    var createdAt: OffsetDateTime? = null,
) {
    companion object {
        fun of(survey: Survey, uuid: String): SurveyResponse {
            return SurveyResponse(
                survey = survey,
                uuid = uuid
            )
        }
    }

    fun addItem(item: SurveyResponseItem) {
        this.items.add(item)
    }
}