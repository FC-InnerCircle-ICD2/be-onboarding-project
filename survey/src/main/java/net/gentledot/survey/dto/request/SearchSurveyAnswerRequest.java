package net.gentledot.survey.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchSurveyAnswerRequest {
    private String surveyId;
    private String questionName;
    private String answerValue;

    public static SearchSurveyAnswerRequest fromRequest(String surveyId, String questionName, String answerValue) {
        return new SearchSurveyAnswerRequest(surveyId, questionName, answerValue);
    }
}
