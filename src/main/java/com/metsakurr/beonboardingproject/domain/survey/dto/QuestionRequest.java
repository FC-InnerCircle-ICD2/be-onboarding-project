package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.common.validation.ValidQuestionType;
import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
public class QuestionRequest {
    private Long idx;

    @NotBlank(message = "name[항목 이름]은 필수 값입니다.")
    private String name;

    @NotBlank(message = "description[항목 설명]은 필수 값입니다.")
    private String description;

    @ValidQuestionType
    private String questionType;

    @NotNull(message = "isRequired[항목 필수 여부]는 필수 값입니다.")
    private Boolean isRequired;

    @Valid
    private List<String> options;

    @AssertTrue(message = "[단일 선택 리스트], [다중 선택 리스트]의 경우 선택 할 수 있는 후보 값이 필요합니다.")
    public boolean isValidOptions() {
        QuestionType type = QuestionType.valueOf(questionType);
        if (QuestionType.SINGLE_CHOICE.equals(type) || QuestionType.MULTI_CHOICE.equals(type)) {
            if (options == null) {
                return false;
            }

            return !options.isEmpty();
        }
        return true;
    }

    public boolean getIsRequired() {
        return this.isRequired;
    }

    public Question toEntity() {
        Question question = Question.builder()
                .name(name)
                .description(description)
                .questionType(QuestionType.valueOf(questionType))
                .isRequired(isRequired)
                .build();
        Optional.ofNullable(options)
                .orElseGet(Collections::emptyList).stream()
                .map(Option::of).toList()
                .forEach(question::addOptions);

        return question;
    }

    @Builder
    protected QuestionRequest(
            String name,
            String description,
            String questionType,
            Boolean isRequired,
            List<String> options
    ) {
        this.name = name;
        this.description = description;
        this.questionType = questionType;
        this.isRequired = isRequired;
        this.options = options;
    }
}