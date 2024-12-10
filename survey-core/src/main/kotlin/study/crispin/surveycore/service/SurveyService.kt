package study.crispin.surveycore.service

import org.springframework.stereotype.Service
import study.crispin.surveycore.domain.Survey
import study.crispin.surveycore.extension.toDomain
import study.crispin.surveycore.extension.toDto
import study.crispin.surveyinfra.port.SaveSurveyPort
import study.crispin.surveyinfra.port.SurveyPort

@Service
internal class SurveyService(
    private val surveyPort: SurveyPort
) : SurveyUseCase {

    override fun createSurvey(request: CreateSurveyUseCase.Request): Survey {
        val saveRequest = SaveSurveyPort.Request(
            request.name,
            request.description,
            request.items.map { it.toDto() },
        )
        return surveyPort.save(saveRequest).toDomain()
    }
}
