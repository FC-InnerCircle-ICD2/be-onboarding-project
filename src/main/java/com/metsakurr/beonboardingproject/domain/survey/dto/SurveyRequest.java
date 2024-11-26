package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SurveyRequest {
    @NotBlank(message = "name[설문조사 이름]은 필수 값입니다.")
    private String name;

    @NotBlank(message = "description[설문조사 설명]은 필수 값입니다.")
    private String description;

    @Valid
    private List<QuestionRequest> questions = new ArrayList<>();

    @AssertTrue(message = "questions[설문 받을 항목]을 입력해 주세요.")
    public boolean isValidQuestions() {
        if (questions.isEmpty()) {
            return false;
        }
        return true;
    }

    public Survey toEntity() {
        return Survey.builder()
                .name(name)
                .description(description)
                .build();
    }
}
