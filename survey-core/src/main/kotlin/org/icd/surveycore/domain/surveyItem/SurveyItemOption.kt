package org.icd.surveycore.domain.surveyItem

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity
@Table(name = "survey_item_option")
class SurveyItemOption(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_item_id")
    val surveyItem: SurveyItem,
    @Comment("선택 후보 이름")
    var name: String,
    @Comment("현재 사용 여부")
    var isActive: Boolean = true
) {
    companion object {
        fun of(
            surveyItem: SurveyItem,
            name: String
        ): SurveyItemOption {
            return SurveyItemOption(
                surveyItem = surveyItem,
                name = name
            )
        }
    }

    fun delete() {
        this.isActive = false
    }

    fun updateName(name: String) {
        this.name = name
    }
}