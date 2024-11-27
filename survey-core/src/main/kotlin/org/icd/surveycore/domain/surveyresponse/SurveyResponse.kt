package org.icd.surveycore.domain.surveyresponse

import jakarta.persistence.*
import org.hibernate.annotations.Comment
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
    val surveyId: Long,
    @Comment("중복 제출 방지용 유니크 아이디")
    val uuid: String,
    @CreatedDate
    var createdAt: OffsetDateTime? = null,
)