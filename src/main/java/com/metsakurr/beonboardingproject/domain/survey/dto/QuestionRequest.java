package com.metsakurr.beonboardingproject.domain.survey.dto;

import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.QuestionType;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionRequest {
    private String name;
    private String description;
    private String questionType;
    private List<OptionRequest> options;

    public Question toEntity() {
        return Question.builder()
                .name(name)
                .description(description)
                .questionType(QuestionType.fromName(questionType))
                .build();
    }
}