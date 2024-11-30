package com.onboarding.beonboardingproject.dto;

import com.onboarding.beonboardingproject.domain.survey.Option;
import com.onboarding.beonboardingproject.domain.survey.Question;
import com.onboarding.beonboardingproject.domain.survey.QuestionType;
import com.onboarding.beonboardingproject.domain.survey.Survey;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SurveyDto {
    private String surveyName;
    private String surveyDesc;
    private List<QuestionDto> questions = new ArrayList<>();  // 기본값으로 빈 리스트 설정;

    public Survey toEntity() {
        Survey survey = Survey.builder()
                .surveyName(surveyName)
                .surveyDesc(surveyDesc)
                .build();

        for (QuestionDto questionDto : questions) {
            Question question = Question.builder()
                    .survey(survey)
                    .questionName(questionDto.getQuestionName())
                    .questionDesc(questionDto.getQuestionDesc())
                    .questionType(QuestionType.valueOf(questionDto.getQuestionType()))
                    .isRequired(questionDto.isRequired())
                    .build();

            if(questionDto.getOptions() != null) {
                for(OptionDto optionDto : questionDto.getOptions()) {
                    Option option = Option.builder()
                            .question(question)
                            .optionValue(optionDto.getOptionValue())
                            .build();
                    question.addOption(option);
                }
            }

            survey.addQuestion(question);
        }
        return survey;
    }
}
