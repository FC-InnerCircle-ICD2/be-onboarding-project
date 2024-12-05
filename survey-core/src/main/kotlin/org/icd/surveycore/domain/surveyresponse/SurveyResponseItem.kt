package org.icd.surveycore.domain.surveyresponse

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.icd.surveycore.domain.support.BaseEntity
import org.icd.surveycore.domain.surveyItem.ItemType

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
    @Comment("항목 순서")
    val sequence: Int,
    @Comment("항목 이름")
    val name: String,
    @Comment("항목 설명")
    val description: String? = null,
    @Comment("항목 입력 형태")
    @Enumerated(EnumType.STRING)
    val itemType: ItemType,
    @Embedded
    val shortResponse: ShortResponse?,
    @Embedded
    val longResponse: LongResponse?,
    @Embedded
    val singleChoiceResponse: SingleChoiceResponse?,
    @Embedded
    val multipleChoiceResponse: MultipleChoiceResponse?,
) : BaseEntity() {
    companion object {
        fun of(
            surveyResponse: SurveyResponse,
            sequence: Int,
            name: String,
            description: String? = null,
            itemType: ItemType,
            shortResponse: ShortResponse? = null,
            longResponse: LongResponse? = null,
            singleChoiceResponse: SingleChoiceResponse? = null,
            multipleChoiceResponse: MultipleChoiceResponse? = null
        ): SurveyResponseItem {
            return SurveyResponseItem(
                surveyResponse = surveyResponse,
                sequence = sequence,
                name = name,
                description = description,
                itemType = itemType,
                shortResponse = shortResponse,
                longResponse = longResponse,
                singleChoiceResponse = singleChoiceResponse,
                multipleChoiceResponse = multipleChoiceResponse
            )
        }
    }
}

@Embeddable
class ShortResponse(
    val shortResponse: String
)

@Embeddable
class LongResponse(
    val longResponse: String
)

@Embeddable
class SingleChoiceResponse(
    val choiceOptionId: Long,
    val choiceOptionName: String,
)

@Embeddable
class ChoiceOption(
    val choiceOptionId: Long,
    val choiceOptionName: String
)

@Embeddable
class MultipleChoiceResponse(
    @ElementCollection
    @CollectionTable(
        name = "multiple_choice_response_options",
        joinColumns = [JoinColumn(name = "survey_response_item_id")]
    )
    val choiceOptions: List<ChoiceOption>
)
