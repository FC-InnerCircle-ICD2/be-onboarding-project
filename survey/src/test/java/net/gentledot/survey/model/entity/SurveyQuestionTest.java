package net.gentledot.survey.model.entity;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SurveyItemTest {

    @DisplayName("entity 생성 구상")
    @Test
    void createSurveyTest() {
        /*Survey - SurveyQuestion - SurveyOption 의 관계 */
        Survey survey = Survey.of(
                "test Survey",
                "desc",
                List.of(
                        SurveyQuestion.of("question1", "this is question1",
                                SurveyItemType.SINGLE_SELECT, ItemRequired.REQUIRED,
                                List.of(SurveyQuestionOption.of("option1"), SurveyQuestionOption.of("option2"))),
                        SurveyQuestion.of("question2", "this is question2",
                                SurveyItemType.TEXT, ItemRequired.OPTIONAL,
                                List.of(SurveyQuestionOption.of("option1"))
                        )));

        log.info("created survey : {}", survey);

        assertThat(survey).isNotNull();
        assertThat(survey.getId()).isNotBlank();
        List<SurveyQuestion> questions = survey.getQuestions();
        assertThat(questions).hasSize(2);
        List<SurveyQuestionOption> options = questions.get(0).getOptions();
        assertThat(options).hasSize(2);
        assertThat(options.get(0).getOptionText()).isEqualTo("option1");

    }
}