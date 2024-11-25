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
    val name: String,
    @Comment("현재 사용 여부")
    var isActive: Boolean = true
)
