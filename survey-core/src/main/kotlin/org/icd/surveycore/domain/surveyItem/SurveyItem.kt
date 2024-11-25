package org.icd.surveycore.domain.surveyItem

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@Entity
@Table(name = "survey_item")
@EntityListeners(AuditingEntityListener::class)
class SurveyItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Comment("설문조사 아이디")
    val surveyId: Long,
    @Comment("항목 이름")
    val name: String,
    @Comment("항목 설명")
    val description: String? = null,
    @Comment("항목 입력 형태")
    @Enumerated(EnumType.STRING)
    val itemType: ItemType = ItemType.SHORT_ANSWER,
    @Comment("선택 항목 리스트")
    @OneToMany(mappedBy = "surveyItem", cascade = [CascadeType.ALL], orphanRemoval = true)
    val options: List<SurveyItemOption> = mutableListOf(),
    @Comment("현재 사용 여부")
    val isActive: Boolean = true,
    @CreatedDate
    var createdAt: OffsetDateTime? = null,
    @LastModifiedDate
    var updatedAt: OffsetDateTime? = null
)