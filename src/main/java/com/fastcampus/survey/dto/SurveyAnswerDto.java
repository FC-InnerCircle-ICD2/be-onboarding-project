package com.fastcampus.survey.dto;

import lombok.Getter;

@Getter
public class SurveyAnswerDto {
    private Long survey_id;
    private Long question_id;
    private String writer;
    private String answer;
}
