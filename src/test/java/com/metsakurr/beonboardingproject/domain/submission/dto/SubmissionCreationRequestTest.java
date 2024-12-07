package com.metsakurr.beonboardingproject.domain.submission.dto;

import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
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
import static org.junit.jupiter.api.Assertions.*;

public class SubmissionCreationRequestTest {
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

    @DisplayName("설문 조사 식별자가 없으면 답변을 생성할 수 없다")
    @Test
    void testAnswerNotCreateIfIdxNull() {
        // given
        SubmissionCreationRequest request = createSubmissionRequestSubmissionIdxNull();

        // when
        Set<ConstraintViolation<SubmissionCreationRequest>> violation = validator.validate(request);

        // then
        assertThat(violation).isNotEmpty();
        violation
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("유효한 idx[응답할 설문 조사 식별자]가 필요합니다.");
                });
    }

    @DisplayName("각 질문 항목의 답변에는 항목 실별자가 필요하다")
    @Test
    void test() {
        // given
        SubmissionCreationRequest request = createSubmissionRequestAnswerIdxNull();

        // when
        Set<ConstraintViolation<SubmissionCreationRequest>> violation = validator.validate(request);

        // then
        assertThat(violation).isNotEmpty();
        violation
                .forEach(error -> {
                    assertThat(error.getMessage()).isEqualTo("유효한 idx[항목 식별자]가 필요합니다.");
                });
    }

    private SubmissionCreationRequest createSubmissionRequestSubmissionIdxNull() {
        return SubmissionCreationRequest.builder()
                .answers(List.of(
                        SubmissionCreationRequest.AnswerRequest.builder()
                                .idx(1)
                                .answer("test")
                                .build()
                ))
                .build();
    }

    private SubmissionCreationRequest createSubmissionRequestAnswerIdxNull() {
        return SubmissionCreationRequest.builder()
                .idx(1)
                .answers(List.of(
                        SubmissionCreationRequest.AnswerRequest.builder()
                                .answer("test")
                                .build()
                ))
                .build();
    }

}