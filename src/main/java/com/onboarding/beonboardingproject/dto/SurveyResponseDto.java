package com.onboarding.beonboardingproject.dto;

import com.onboarding.beonboardingproject.domain.survey.Option;
import com.onboarding.beonboardingproject.domain.survey.Question;
import com.onboarding.beonboardingproject.domain.survey.Survey;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SurveyResponseDto {
    private final Long surveyId;
    private final String surveyName;
    private final String surveyDesc;
    private final List<QuestionDto> questions;

    public SurveyResponseDto(Survey survey) {

        this.surveyId = survey.getId();
        this.surveyName = survey.getSurveyName();
        this.surveyDesc = survey.getSurveyDesc();
        // 질문이 있을 경우만 questionDtos를 초기화
        this.questions = (survey.getQuestions() != null) ? listOf(survey.getQuestions()) : new ArrayList<>();
    }

    public static QuestionDto of(Question question) {
        List<OptionDto> optionDtos = new ArrayList<>();

        // 옵션이 null이 아닐 때만 추가
        if (question.getOptions() != null) {
            for (Option option : question.getOptions()) {
                optionDtos.add(OptionDto.of(option));
            }
        }
        return new QuestionDto(question.getId(),question.getQuestionName(),question.getQuestionDesc(), question.getQuestionType().getName(),question.isRequired(),optionDtos);
    }

    public static List<QuestionDto> listOf(List<Question> questions){
        List<QuestionDto> questionDtos = new ArrayList<>();

        for(Question question : questions) {
            questionDtos.add(of(question));
        }
        return questionDtos;
    }
}
