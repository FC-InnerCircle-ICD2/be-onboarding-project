package org.icd.surveycore.domain.survey

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@Entity
@Table(name = "survey")
@EntityListeners(AuditingEntityListener::class)
class Survey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Comment("설문조사 이름")
    val name: String,
    @Comment("설문조사 설명")
    val description: String? = null,
    @CreatedDate
    var createdAt: OffsetDateTime? = null,
    @LastModifiedDate
    var updatedAt: OffsetDateTime? = null
)