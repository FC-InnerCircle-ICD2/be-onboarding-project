package study.crispin.surveyinfra.repository.entity

import java.util.UUID

data class SurveyItemEntity(
    val surveyId: UUID,
    var name: String,
    var description: String,
    val form: FormEmbeddable,
    var required: Boolean,
) {
    var id: UUID? = null
        private set
    var version: Int = 0
        private set

    constructor(
        surveyId: UUID,
        name: String,
        description: String,
        form: FormEmbeddable,
        required: Boolean,
        version: Int
    ) : this(surveyId, name, description, form, required) {
        this.version = version
    }

    companion object {
        fun createWithId(
            surveyId: UUID,
            name: String,
            description: String,
            form: FormEmbeddable,
            required: Boolean,
            id: UUID,
        ): SurveyItemEntity {
            return SurveyItemEntity(
                surveyId,
                name,
                description,
                form,
                required,
            ).apply {
                this.id = id
            }
        }
    }
}
