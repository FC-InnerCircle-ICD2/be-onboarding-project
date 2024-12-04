package com.metsakurr.beonboardingproject.domain.survey.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SurveyRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
    }

    @Test
    @DisplayName("설문 이름이 없는 설문조사는 생성할 수 없다")
    public void testSurveyNotCreateIfNameNull() {
        // given
        SurveyRequest request = createSurveyRequest(null, "설문 이름");

        // when
        Set<ConstraintViolation<SurveyRequest>> violation = validator.validate(request);

        // then
        assertThat(violation).isNotEmpty();
        violation
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("name[설문조사 이름]은 필수 값입니다.");
                });
    }

    @Test
    @DisplayName("설문 설명이 없는 설문조사는 생성할 수 없다")
    public void testSurveyNotCreateIfDescriptionNull() {
        // given
        SurveyRequest request = createSurveyRequest("설문 이름", null);

        // when
        Set<ConstraintViolation<SurveyRequest>> violation = validator.validate(request);

        // then
        assertThat(violation).isNotEmpty();
        violation
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("description[설문조사 설명]은 필수 값입니다.");
                });
    }



    private SurveyRequest createSurveyRequest(String name, String description) {
        return createSurvey(name, description, List.of(
                QuestionRequest.builder()
                        .name("설문 항목")
                        .description("설문 항목 설명")
                        .questionType("SHORT_SENTENCE")
                        .isRequired(true)
                        .build()
        ));
    }

    private SurveyRequest createSurvey(String name, String description, List<QuestionRequest> questions) {
        return SurveyRequest.builder()
                .name(name)
                .description(description)
                .questions(questions)
                .build();
    }
}