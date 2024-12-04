package com.metsakurr.beonboardingproject.domain.survey.repository;

import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.QuestionType;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SurveyRepositoryTest {
    @Autowired
    private SurveyRepository surveyRepository;

    @DisplayName("설문조사를 저장한다.")
    @Test
    void testSave() {
        // given
        Survey survey = createSurvey();

        // when
        Survey savedSurvey = surveyRepository.save(survey);

        // then
        assertThat(savedSurvey)
                .extracting("id", "name", "description")
                .containsExactlyInAnyOrder(1L, "설문조사 이름", "설문조사 설명");

    }

    private Survey createSurvey() {
        return Survey.builder()
                .name("설문조사 이름")
                .description("설문조사 설명")
                .questions(
                        List.of(
                                Question.builder()
                                        .name("항문 이름")
                                        .description("항문 설명")
                                        .questionType(QuestionType.LONG_SENTENCE)
                                        .isRequired(true)
                                        .build()
                        )
                )
                .build();
    }
}