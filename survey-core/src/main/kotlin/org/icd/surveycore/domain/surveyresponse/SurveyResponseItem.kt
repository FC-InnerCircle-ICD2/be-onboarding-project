package org.icd.surveycore.domain.surveyresponse

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.icd.surveycore.domain.support.BaseEntity

@Entity
@Table(name = "survey_response_item")
class SurveyResponseItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Comment("설문조사 응답 아이디")
    @ManyToOne
    @JoinColumn(name = "survey_response_id", nullable = false)
    val surveyResponse: SurveyResponse,
    @Comment("설문조사 항목 아이디")
    val surveyItemId: Long,
    @Comment("텍스트 응답인 경우 응답값")
    val answer: String? = null,
    @Comment("단건 선택인 경우 응답값")
    val itemOptionId: Long? = null,
    @Comment("다건 선택인 경우 응답값")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "survey_response_item_option",
        joinColumns = [JoinColumn(name = "survey_response_item_id")]
    )
    @Column(name = "item_option_id")
    val itemOptionIds: List<Long>? = mutableListOf(),
) : BaseEntity() {
    companion object {
        fun of(
            surveyResponse: SurveyResponse,
            surveyItemId: Long,
            answer: String?,
            itemOptionId: Long?,
            itemOptionIds: List<Long>? = mutableListOf()
        ): SurveyResponseItem {
            return SurveyResponseItem(
                surveyResponse = surveyResponse,
                surveyItemId = surveyItemId,
                answer = answer,
                itemOptionId = itemOptionId,
                itemOptionIds = itemOptionIds
            )
        }
    }
}