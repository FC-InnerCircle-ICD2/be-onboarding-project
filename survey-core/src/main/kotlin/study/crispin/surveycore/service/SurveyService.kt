package study.crispin.surveycore.service

import org.springframework.stereotype.Service
import study.crispin.surveycore.domain.Survey

@Service
internal class SurveyService : SurveyUseCase {

    override fun createSurvey(request: CreateSurveyUseCase.Request): Survey =
        Survey(
            name = request.name,
            description = request.description,
            items = request.items
        )
}
