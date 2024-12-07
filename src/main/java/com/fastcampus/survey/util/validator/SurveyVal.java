package com.fastcampus.survey.util.validator;

import com.fastcampus.survey.dto.QuestionDto;
import com.fastcampus.survey.dto.SurveyDto;

import java.util.List;

public class SurveyVal {
    public static void validateSurvey(SurveyDto surveyDto) {
        validQuestions(surveyDto.getQuestions());
    }

    private static void validQuestions(List<QuestionDto> questionList) {
        for (QuestionDto questionDto : questionList) {
            QuestionVal.validateQuestion(questionDto);
        }
    }
}
