package org.icd.surveycore.domain.survey

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.icd.surveycore.domain.surveyItem.SurveyItem
import org.icd.surveycore.domain.surveyresponse.SurveyResponse
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.AbstractAggregateRoot
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<SurveyItem> = mutableListOf(),
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    val responses: MutableList<SurveyResponse> = mutableListOf(),
    @CreatedDate
    var createdAt: OffsetDateTime? = null,
    @LastModifiedDate
    var updatedAt: OffsetDateTime? = null
) : AbstractAggregateRoot<Survey>() {
    companion object {
        fun of(
            name: String,
            description: String?,
        ): Survey {
            return Survey(
                name = name,
                description = description
            )
        }
    }

    fun addItem(item: SurveyItem) {
        this.items.add(item)
    }

    fun getActiveItems(): List<SurveyItem> {
        return items.filter { it.isActive }
    }
}