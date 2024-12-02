package net.gentledot.survey.repository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.model.entity.surveybase.Survey;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestionOption;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase
@DataJpaTest
class SurveyRepositoryTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @DisplayName("Survey를 생성하고 저장하는 로직 구상")
    @Test
    @Transactional
    void createAndSaveSurveyTest() {
        // option 생성
        SurveyQuestionOption option1 = SurveyQuestionOption.of("option1");
        SurveyQuestionOption option2 = SurveyQuestionOption.of("option2");
        SurveyQuestionOption option3 = SurveyQuestionOption.of("option3");

        // question 생성
        SurveyQuestion question1 = SurveyQuestion.of(
                "question1",
                "this is question1",
                SurveyItemType.SINGLE_SELECT,
                ItemRequired.REQUIRED,
                List.of(option1, option2)
        );

        SurveyQuestion question2 = SurveyQuestion.of(
                "question2",
                "this is question2",
                SurveyItemType.TEXT,
                ItemRequired.OPTIONAL,
                List.of(option3)
        );

        // question-option 으로 Survey 생성
        Survey survey = Survey.of(
                "test Survey",
                "desc",
                List.of(question1, question2)
        );

        // Save Survey
        Survey savedSurvey = surveyRepository.save(survey);

        log.info("created survey : {}", savedSurvey);

        // Assertions
        assertThat(savedSurvey).isNotNull();
        assertThat(savedSurvey.getId()).isNotBlank();
        List<SurveyQuestion> questions = savedSurvey.getQuestions();
        assertThat(questions).hasSize(2);
        assertThat(questions.getFirst().getId()).isNotNull();
        List<SurveyQuestionOption> options = questions.getFirst().getOptions();
        assertThat(options).hasSize(2);
        assertThat(options.getFirst().getId()).isNotNull();
        assertThat(options.getFirst().getOptionText()).isEqualTo("option1");
    }
}