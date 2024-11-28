package net.gentledot.survey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchSurveyAnswerRequest {
    private String surveyId;
    private String questionName;
    private String answerValue;
}
