package study.crispin.surveyinfra.adaptor

import org.springframework.stereotype.Component
import study.crispin.surveyinfra.adaptor.dto.SurveyDto
import study.crispin.surveyinfra.adaptor.dto.SurveyItemDto
import study.crispin.surveyinfra.extension.toEntity
import study.crispin.surveyinfra.port.SaveSurveyPort
import study.crispin.surveyinfra.port.SurveyPort
import study.crispin.surveyinfra.repository.SurveyItemRepository
import study.crispin.surveyinfra.repository.SurveyRepository

@Component
internal class SurveyAdaptor(
    private val surveyRepository: SurveyRepository,
    private val surveyItemRepository: SurveyItemRepository,
) : SurveyPort {
    override fun save(request: SaveSurveyPort.Request): SurveyDto {
        val surveyDto: SurveyDto = surveyRepository.save(request.toEntity())
        val surveyItemDtos: List<SurveyItemDto> = surveyItemRepository.saveAll(
            request.items.map {
                it.toEntity(surveyDto.id)
            }
        )
        return surveyDto.update(surveyItemDtos)
    }
}
