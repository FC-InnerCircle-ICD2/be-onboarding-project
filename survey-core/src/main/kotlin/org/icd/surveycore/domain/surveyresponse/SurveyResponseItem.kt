package org.icd.surveycore.domain.surveyresponse

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@Entity
@Table(name = "survey_response_item")
@EntityListeners(AuditingEntityListener::class)
class SurveyResponseItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Comment("설문조사 아이디")
    val surveyResponseId: Long,
    @Comment("텍스트 응답인 경우 응답값")
    val answer: String? = null,
    @Comment("단건 선택인 경우 응답값")
    val surveyItemOptionId: Long? = null,
    @Comment("다건 선택인 경우 응답값")
    @ElementCollection(fetch = FetchType.EAGER)
    val options: List<Long> = mutableListOf(),
    @CreatedDate
    var createdAt: OffsetDateTime? = null,
)