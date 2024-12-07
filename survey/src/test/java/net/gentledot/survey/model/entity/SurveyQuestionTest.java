package net.gentledot.survey.model.entity;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.domain.enums.ItemRequired;
import net.gentledot.survey.domain.enums.SurveyItemType;
import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;
import net.gentledot.survey.domain.surveybase.SurveyQuestionOption;
import net.gentledot.survey.domain.surveybase.dto.SurveyQuestionOptionDto;
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
        SurveyItemType singleSelect = SurveyItemType.SINGLE_SELECT;
        SurveyItemType text = SurveyItemType.TEXT;
        Survey survey = Survey.of(
                "test Survey",
                "desc",
                List.of(
                        SurveyQuestion.of("question1", "this is question1",
                                singleSelect, ItemRequired.REQUIRED,
                                List.of(SurveyQuestionOption.from(new SurveyQuestionOptionDto("option1")), SurveyQuestionOption.from(new SurveyQuestionOptionDto("option2")))),
                        SurveyQuestion.of("question2", "this is question2",
                                text, ItemRequired.OPTIONAL,
                                List.of(SurveyQuestionOption.from(new SurveyQuestionOptionDto("option1")))
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