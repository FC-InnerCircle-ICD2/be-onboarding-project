package com.metsakurr.beonboardingproject.domain.survey.service;

import com.metsakurr.beonboardingproject.common.dto.ApiResponse;
import com.metsakurr.beonboardingproject.domain.survey.dto.QuestionRequest;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyCreationResponse;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import com.metsakurr.beonboardingproject.domain.survey.entity.QuestionType;
import com.metsakurr.beonboardingproject.domain.survey.repository.SurveyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SurveyServiceTest {
    @Autowired
    private SurveyService surveyService;

    @Test
    @DisplayName("설문조사를 생성한다.")
    public void createSurvey() {
        // given
        SurveyRequest request = createSurveyRequest();

        // when
        ApiResponse<SurveyCreationResponse> response = surveyService.create(request);

        // then
        assertThat(response.getData().getIdx()).isEqualTo(1L);
        assertThat(response.getData().getName()).isEqualTo("설문조사 이름");
        assertThat(response.getData().getDescription()).isEqualTo("설문조사 설명");
        assertThat(response.getData().getQuestions()).hasSize(4);
        assertThat(response.getData().getQuestions().get(3).getOptions()).hasSize(2);
    }

    @Test
    @DisplayName("설문조사를 업데이트 한다.")
    public void updateSurvey() {
//        // given
//        surveyService.create(createSurveyRequest());
//        SurveyRequest request = updatedSurveyRequest();
//
//        // when
//        ApiResponse<SurveyCreationResponse> response = surveyService.update(request);
//
//        // then
//        assertThat(response.getData().getIdx()).isEqualTo(1L);
//        assertThat(response.getData().getName()).isEqualTo("테스트 설문 이름(수정됨)");
//        assertThat(response.getData().getDescription()).isEqualTo("테스트 설문 설명(수정됨)");
//        assertThat(response.getData().getQuestions()).hasSize(3);
//        assertThat(response.getData().getQuestions().get(2).getIdx()).isNotEqualTo(2L);
//        assertThat(response.getData().getQuestions().get(2).getOptions()).hasSize(3);
//        assertThat(response.getData().getQuestions().get(3).getOptions().get(0).getName()).isEqualTo("옵션 변경");
    }

    private SurveyRequest createSurveyRequest() {
        return SurveyRequest.builder()
                .name("설문조사 이름")
                .description("설문조사 설명")
                .questions(
                        List.of(
                                QuestionRequest.builder()
                                        .name("질문 1: 단답형")
                                        .description("질문 1 설명")
                                        .questionType(QuestionType.SHORT_SENTENCE.name())
                                        .isRequired(false)
                                        .build(),
                                QuestionRequest.builder()
                                        .name("질문 2: 장문형")
                                        .description("질문 2 설명")
                                        .questionType(QuestionType.LONG_SENTENCE.name())
                                        .isRequired(false)
                                        .build(),
                                QuestionRequest.builder()
                                        .name("질문 3: 단일 선택 리스트")
                                        .description("질문 3 설명")
                                        .questionType(QuestionType.SINGLE_CHOICE.name())
                                        .isRequired(false)
                                        .options(
                                                List.of("옵션3-1", "옵션3-2")
                                        )
                                        .build(),
                                QuestionRequest.builder()
                                        .name("질문 4: 다중 선택 리스트")
                                        .description("질문 4 설명")
                                        .questionType(QuestionType.MULTI_CHOICE.name())
                                        .isRequired(false)
                                        .options(
                                                List.of("옵션4-1", "옵션4-2")
                                        )
                                        .build()
                        )
                )
                .build();
    }

    private SurveyRequest updatedSurveyRequest() {
        return SurveyRequest.builder()
                .idx(1)
                .name("테스트 설문 이름(수정됨)")
                .description("테스트 설문 설명(수정됨)")
                .questions(
                        List.of(
                                // 변경되지 않음
                                QuestionRequest.builder()
                                        .idx(1L)
                                        .name("질문 1: 단답형")
                                        .description("질문 1 설명")
                                        .questionType(QuestionType.SHORT_SENTENCE.name())
                                        .isRequired(false)
                                        .build(),
                                // 장문형에서 다중 선택 리스트로 변경
                                QuestionRequest.builder()
                                        .idx(2L)
                                        .name("질문 2 : 다중 선택 리스트(수정됨)")
                                        .description("질문 2 설명 (수정됨)")
                                        .questionType(QuestionType.MULTI_CHOICE.name())
                                        .isRequired(false)
                                        .options(List.of("옵션 1", "옵션 2", "옵션 3"))
                                        .build(),
                                // 단일 선택 리스트
                                QuestionRequest.builder()
                                        .name("질문 3: 단일 선택 리스트")
                                        .description("질문 3 설명")
                                        .questionType(QuestionType.SINGLE_CHOICE.name())
                                        .isRequired(false)
                                        .options(
                                                List.of("옵션 변경")
                                        )
                                        .build()
                        )
                )
                .build();
    }
}