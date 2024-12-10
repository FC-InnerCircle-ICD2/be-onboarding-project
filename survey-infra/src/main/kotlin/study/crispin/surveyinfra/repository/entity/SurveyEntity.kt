package study.crispin.surveyinfra.repository.entity

import java.util.UUID

data class SurveyEntity(
    var name: String,
    var description: String,
) {
    var id: UUID? = null
        private set

    companion object {
        fun createWithId(
            name: String,
            description: String,
            id: UUID,
        ): SurveyEntity {
            return SurveyEntity(name, description).apply {
                this.id = id
            }
        }
    }

    fun update(entity: SurveyEntity): SurveyEntity {
        this.id = entity.id
        this.name = entity.name
        this.description = entity.description
        return this
    }
}
