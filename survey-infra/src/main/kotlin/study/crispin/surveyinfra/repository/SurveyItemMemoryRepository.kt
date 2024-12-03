package study.crispin.surveyinfra.repository

import java.util.UUID
import org.springframework.stereotype.Repository
import study.crispin.surveyinfra.repository.entity.SurveyItemEntity

@Repository
internal class SurveyItemMemoryRepository : SurveyItemRepository {

    private val storage: MutableMap<UUID, SurveyItemEntity> = mutableMapOf()

    override fun saveAll(entities: List<SurveyItemEntity>): List<SurveyItemEntity> {
        return entities.map {
            when (it.id) {
                null -> it.run {
                    val newId: UUID = UUID.randomUUID()
                    SurveyItemEntity.createWithId(
                        surveyId,
                        name,
                        description,
                        form,
                        required,
                        newId,
                    ).also { storage[newId] = this }
                }

                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun findBySurveyId(id: UUID): List<SurveyItemEntity> {
        val maxVersion: Int = storage.values
            .filter { it.surveyId == id }
            .maxOf { it.version }
        return storage.values
            .filter { it.surveyId == id && it.version == maxVersion}
    }
}
