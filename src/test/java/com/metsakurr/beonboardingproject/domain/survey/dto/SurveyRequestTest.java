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
import java.util.stream.IntStream;

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

    @Test
    @DisplayName("설문 받을 항목은 1개 이상이어야 한다")
    public void testSurveyNotCreateQuestionNull() {
        // given
        SurveyRequest request = createSurvey(null);

        // when
        Set<ConstraintViolation<SurveyRequest>> violation = validator.validate(request);

        // then
        assertThat(violation).isNotEmpty();
        violation
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("questions[설문 받을 항목]은 1개 이상, 10개 이하여야 합니다.");
                });
    }

    @Test
    @DisplayName("설문 받을 항목은 10개 이하여야 한다")
    public void testSurveyMoreThan10Question() {
        // given
        SurveyRequest request = createSurvey(
                IntStream.rangeClosed(1, 11)
                        .mapToObj(id -> createQuestion())
                        .toList()
        );

        // when
        Set<ConstraintViolation<SurveyRequest>> violation = validator.validate(request);

        // then
        assertThat(violation).isNotEmpty();
        violation
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("questions[설문 받을 항목]은 1개 이상, 10개 이하여야 합니다.");
                });
    }

    @Test
    @DisplayName("항목 이름이 없는 설문 항목은 생성할 수 없다")
    public void testQuestionNotCreateIfNameNull() {

    }

    @Test
    @DisplayName("항목 설명이 없는 설문 항목은 생성할 수 없다")
    public void testQuestionNotCreateIfDescriptionNull() {}

    @Test
    @DisplayName("항목입력 형태는 [SHORT_SENTENCE], [LONG_SENTENCE], [SINGLE_CHOICE], [MULTI_CHOICE]이어야 한다.")
    public void testQuestionNotCreateIfQuestionTypeInvalid() {

    }

    @Test
    @DisplayName("단일 선택리스트의 경우 선택할 수 있는 후보 값이 필요하다")
    public void testSingleChoiceQuesionNotCreateIfOptionsNull() {

    }

    @Test
    @DisplayName("다중 선택 리스트의 경우 선택할 수 있는 후보 값이 필요하다")
    public void testMultiChoiceQuestionNotCreateIfOptionsNull() {

    }

    private SurveyRequest createSurvey(List<QuestionRequest> questions) {
        return createSurvey("이름", "설명", questions);
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

    private QuestionRequest createQuestion() {
        return QuestionRequest.builder()
                .name("설문 항목")
                .description("설문 항목 설명")
                .questionType("SHORT_SENTENCE")
                .isRequired(true)
                .build();
    }
}