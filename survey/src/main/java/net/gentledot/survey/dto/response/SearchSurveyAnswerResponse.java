package net.gentledot.survey.dto.response;

import java.util.List;

public record SearchSurveyAnswerResponse(
        String surveyId,
        List<SurveyAnswerValue> answerList
) {
}
