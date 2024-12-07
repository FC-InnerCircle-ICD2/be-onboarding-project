package com.innercicle.application.service.v1;

import com.innercicle.adapter.in.web.survey.v1.request.RegisterSurveyItemRequestV1;
import com.innercicle.adapter.in.web.survey.v1.request.RegisterSurveyRequestV1;
import com.innercicle.application.port.out.v1.RegisterSurveyOutPortV1;
import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.Survey;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterSurveyServiceV1Test {

    @Mock
    private RegisterSurveyOutPortV1 registerSurveyOutPortV1;

    @InjectMocks
    private RegisterSurveyServiceV1 registerSurveyServiceV1;

    @Test
    @DisplayName("설문 등록 서비스 - 성공")
    void registerSurvey() {
        // given
        List<RegisterSurveyItemRequestV1> items = new ArrayList<>();
        items.add(RegisterSurveyItemRequestV1.builder()
                      .item("테스트 항목")
                      .description("테스트 항목 설명")
                      .required(true)
                      .type(InputType.SHORT_TEXT)
                      .options(List.of("선택지1", "선택지2"))
                      .build());
        items.add(RegisterSurveyItemRequestV1.builder()
                      .item("테스트 항목2")
                      .description("테스트 항목 설명2")
                      .required(false)
                      .type(InputType.MULTI_SELECT)
                      .options(List.of("선택지1", "선택지2"))
                      .build());
        RegisterSurveyRequestV1 request = RegisterSurveyRequestV1.builder()
            .name("테스트 설문")
            .description("테스트 설문 설명")
            .items(items)
            .build();
        // when
        Survey mockSurvey = Survey.builder().id(1L).name("테스트 설문").description("테스트 설문 설명").items(new ArrayList<>()).build();
        when(registerSurveyOutPortV1.registerSurvey(any())).thenReturn(mockSurvey);
        Survey survey = registerSurveyServiceV1.registerSurvey(request.mapToCommand());
        // then
        assertThat(survey).isNotNull();
    }

    @Test
    @DisplayName("설문 조사 실패 - 필수 항목 누락")
    void registerSurvey_fail() {
        // given
        RegisterSurveyRequestV1 requestV1 = RegisterSurveyRequestV1.builder().build();
        // when
        assertThatThrownBy(() -> registerSurveyServiceV1.registerSurvey(requestV1.mapToCommand()))
            .isInstanceOf(ConstraintViolationException.class)
            .satisfies(exception -> {
                List<String> messages = extractConstraintViolationMessages((ConstraintViolationException)exception);
                assertThat(messages).containsExactlyInAnyOrder(
                    "설문 명은 필수 입니다.",
                    "설문 설명은 필수 입니다.",
                    "설문 항목 목록은 필수 입니다."
                );
            });

        // then

    }

    @Test
    @DisplayName("설문 항목 등록 실패 - 설문 항목 목록 누락")
    void registerSurvey_fail_items_size_zero() {
        // given
        RegisterSurveyRequestV1 requestV1 = RegisterSurveyRequestV1.builder()
            .name("테스트 설문")
            .description("테스트 설문 설명")
            .build();

        // when
        assertThatThrownBy(() -> registerSurveyServiceV1.registerSurvey(requestV1.mapToCommand()))
            .isInstanceOf(ConstraintViolationException.class)
            .satisfies(exception -> {
                List<String> messages = extractConstraintViolationMessages((ConstraintViolationException)exception);
                assertThat(messages).containsExactlyInAnyOrder(
                    "설문 항목 목록은 필수 입니다."
                );
            });

    }

    @Test
    @DisplayName("설문 항목 등록 실패 - 아이템 목록 필수 항목 누락")
    void registerSurvey_fail_items_omit_required_field() {
        // given
        RegisterSurveyRequestV1 requestV1 = RegisterSurveyRequestV1.builder()
            .name("테스트 설문")
            .description("테스트 설문 설명")
            .items(List.of(RegisterSurveyItemRequestV1.builder().build()))
            .build();

        // when
        assertThatThrownBy(() -> registerSurveyServiceV1.registerSurvey(requestV1.mapToCommand()))
            .isInstanceOf(ConstraintViolationException.class)
            .satisfies(exception -> {
                List<String> messages = extractConstraintViolationMessages((ConstraintViolationException)exception);
                assertThat(messages).containsExactlyInAnyOrder(
                    "설문 항목 명은 필수 입니다.",
                    "설문 항목 설명은 필수 입니다.",
                    "설문 항목 타입은 필수 입니다.",
                    "설문 항목 선택지 목록은 필수 입니다."
                );
            });

    }

    private List<String> extractConstraintViolationMessages(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .toList();
    }

}