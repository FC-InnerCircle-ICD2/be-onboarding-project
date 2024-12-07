package com.fastcampus.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyAnswerDetailDto {
    private Long surveyID;
    private String surveyName;
    private String surveyDesc;
    private List<QuestionAnsDto> questionAnsList;
}
