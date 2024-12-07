package com.survey.www.surveys.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SurveyAnswerDetailRequest {
    private String questionNm;
    private String optionContent;
    private String answerContent;
}
