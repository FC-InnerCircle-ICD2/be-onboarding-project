package org.icd.surveycore.domain.surveyItem

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.icd.surveycore.domain.support.BaseEntity
import org.icd.surveycore.domain.survey.Survey

@Entity
@Table(name = "survey_item")
@EntityListeners(AuditingEntityListener::class)
class SurveyItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Comment("설문조사 아이디")
    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    val survey: Survey,
    @Comment("항목 순서")
    val sequence: Int,
    @Comment("항목 이름")
    val name: String,
    @Comment("항목 설명")
    val description: String? = null,
    @Comment("항목 입력 형태")
    @Enumerated(EnumType.STRING)
    val itemType: ItemType = ItemType.SHORT_ANSWER,
    @Comment("선택 항목 리스트")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "surveyItem", cascade = [CascadeType.ALL], orphanRemoval = true)
    val options: MutableList<SurveyItemOption> = mutableListOf(),
    @Comment("현재 사용 여부")
    val isActive: Boolean = true,
    @Comment("필수 응답값 여부")
    val isRequired: Boolean = true,
) : BaseEntity() {
    companion object {
        fun of(
            survey: Survey,
            sequence: Int,
            name: String,
            description: String? = null,
            itemType: ItemType,
            isRequired: Boolean,
        ): SurveyItem {
            return SurveyItem(
                survey = survey,
                sequence = sequence,
                name = name,
                description = description,
                itemType = itemType,
                isRequired = isRequired,
            )
        }
    }

    fun addOptions(surveyItemOptions: List<SurveyItemOption>?) {
        surveyItemOptions?.let { this.options.addAll(it) }
    }

    fun getActiveOptions(): List<SurveyItemOption> {
        return options.filter { it.isActive }
    }
}