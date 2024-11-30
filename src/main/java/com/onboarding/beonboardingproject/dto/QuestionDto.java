package com.onboarding.beonboardingproject.dto;

import com.onboarding.beonboardingproject.domain.response.Answer;
import com.onboarding.beonboardingproject.domain.response.Response;
import com.onboarding.beonboardingproject.domain.survey.Option;
import com.onboarding.beonboardingproject.domain.survey.Question;
import com.onboarding.beonboardingproject.domain.survey.QuestionType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDto {
    //설문 항목 생성 및 수정에 사용

    private Long questionId;
    private String questionName;
    private String questionDesc;
    private String questionType;
    private boolean isRequired;
    private List<OptionDto> options;

    public QuestionDto(Long questionId, String questionName, String questionDesc, String questionType, boolean isRequired, List<OptionDto> options) {
        this.questionId = questionId;
        this.questionName = questionName;
        this.questionDesc = questionDesc;
        this.questionType = questionType;
        this.isRequired = isRequired;
        this.options = options;
    }

    public Question toEntity() {
        // String -> QuestionType 변환 시 예외 처리
        QuestionType type;
        try {
            type = QuestionType.fromName(questionType);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid QuestionType: " + questionType);
        }

        Question question = Question.builder()
                .questionName(questionName)
                .questionDesc(questionDesc)
                .questionType(type)
                .isRequired(isRequired)
                .build();

        if (options != null) {
            for (OptionDto optionDto : options) {
                Option option = Option.builder()
                        .question(question)
                        .optionValue(optionDto.getOptionValue())  // 옵션 값을 String으로 처리
                        .build();
                question.addOption(option);
            }
        }
        return question;
    }
}
