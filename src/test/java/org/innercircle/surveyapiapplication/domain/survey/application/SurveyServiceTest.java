package org.innercircle.surveyapiapplication.domain.survey.application;

import jakarta.persistence.EntityManager;
import org.innercircle.surveyapiapplication.domain.surveyItem.application.SurveyItemService;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.ParagraphSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.TextSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SingleChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.fixture.SurveyItemFixture;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.fixture.SurveyFixture;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyUpdateRequest;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SurveyServiceTest {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyItemService surveyItemService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("[SUCCESS] 단답형 설문항목을 가진 설문조사를 생성할 수 있다.")
    void createSurveyWithShortAnswerQuestion() {
        // given
        TextSurveyItem question = SurveyItemFixture.createTextSurveyItem();
        Survey survey = SurveyFixture.createSurvey(List.of(question));

        // when
        surveyService.createSurvey(survey);
        entityManager.flush();

        // then
        Survey savedSurvey = surveyService.findById(survey.getId());
        TextSurveyItem savedQuestion = (TextSurveyItem) surveyItemService.findByIdAndVersion(survey.getId(), question.getId(), question.getVersion());

        assertThat(savedSurvey.getId()).isEqualTo(survey.getId());
        assertThat(savedSurvey.getName()).isEqualTo(survey.getName());
        assertThat(savedSurvey.getDescription()).isEqualTo(survey.getDescription());
        assertThat(savedSurvey.getSurveyItems()).containsAll(survey.getSurveyItems());

        assertThat(savedQuestion.getId()).isEqualTo(question.getId());
        assertThat(savedQuestion.getVersion()).isEqualTo(question.getVersion());
        assertThat(savedQuestion.getName()).isEqualTo(question.getName());
        assertThat(savedQuestion.getDescription()).isEqualTo(question.getDescription());
        assertThat(savedQuestion.getSurveyId()).isEqualTo(survey.getId());
    }

    @Test
    @DisplayName("[SUCCESS] 장문형 설문항목을 가진 설문조사를 생성할 수 있다.")
    void createSurveyWithLongAnswerQuestion() {
        // given
        ParagraphSurveyItem question = SurveyItemFixture.createParagraphSurveyItem();
        Survey survey = SurveyFixture.createSurvey(List.of(question));
        final Long surveyId = survey.getId();
        final Long questionId = question.getId();

        // when
        surveyService.createSurvey(survey);

        // then
        Survey savedSurvey = surveyService.findById(surveyId);
        ParagraphSurveyItem savedQuestion = (ParagraphSurveyItem) surveyItemService.findByIdAndVersion(
            survey.getId(), question.getId(), question.getVersion());

        assertThat(savedSurvey.getId()).isEqualTo(surveyId);
        assertThat(savedSurvey.getName()).isEqualTo(survey.getName());
        assertThat(savedSurvey.getDescription()).isEqualTo(survey.getDescription());
        assertThat(savedSurvey.getSurveyItems()).containsAll(survey.getSurveyItems());

        assertThat(savedQuestion.getId()).isEqualTo(questionId);
        assertThat(savedQuestion.getName()).isEqualTo(question.getName());
        assertThat(savedQuestion.getDescription()).isEqualTo(question.getDescription());
        assertThat(savedQuestion.getSurveyId()).isEqualTo(surveyId);
    }

    @Test
    @DisplayName("[SUCCESS] 단항선택형 설문항목을 가진 설문조사를 생성할 수 있다.")
    void createSurveyWithSingleChoiceQuestion() {
        // given
        SingleChoiceSurveyItem question = SurveyItemFixture.createSingleChoiceSurveyItem();
        Survey survey = SurveyFixture.createSurvey(List.of(question));
        final Long surveyId = survey.getId();
        final Long questionId = question.getId();

        // when
        surveyService.createSurvey(survey);

        // then
        Survey savedSurvey = surveyService.findById(surveyId);
        SingleChoiceSurveyItem savedQuestion = (SingleChoiceSurveyItem) surveyItemService.findByIdAndVersion(
            survey.getId(), question.getId(), question.getVersion());

        assertThat(savedSurvey.getId()).isEqualTo(surveyId);
        assertThat(savedSurvey.getName()).isEqualTo(survey.getName());
        assertThat(savedSurvey.getDescription()).isEqualTo(survey.getDescription());
        assertThat(savedSurvey.getSurveyItems()).containsAll(survey.getSurveyItems());

        assertThat(savedQuestion.getId()).isEqualTo(questionId);
        assertThat(savedQuestion.getName()).isEqualTo(question.getName());
        assertThat(savedQuestion.getDescription()).isEqualTo(question.getDescription());
        assertThat(savedQuestion.getOptions()).containsAll(question.getOptions());
        assertThat(savedQuestion.getSurveyId()).isEqualTo(surveyId);
    }

    @Test
    @DisplayName("[SUCCESS] 다항선택형 설문항목을 가진 설문조사를 생성할 수 있다.")
    void createSurveyWithMultiChoiceQuestion() {
        // given
        MultiChoiceSurveyItem question = SurveyItemFixture.createMultiChoiceSurveyItem();
        Survey survey = SurveyFixture.createSurvey(List.of(question));
        final Long surveyId = survey.getId();
        final Long questionId = question.getId();

        // when
        surveyService.createSurvey(survey);

        // then
        Survey savedSurvey = surveyService.findById(surveyId);
        MultiChoiceSurveyItem savedQuestion = (MultiChoiceSurveyItem) surveyItemService.findByIdAndVersion(
            survey.getId(), question.getId(), question.getVersion());

        assertThat(savedSurvey.getId()).isEqualTo(surveyId);
        assertThat(savedSurvey.getName()).isEqualTo(survey.getName());
        assertThat(savedSurvey.getDescription()).isEqualTo(survey.getDescription());
        assertThat(savedSurvey.getSurveyItems()).containsAll(survey.getSurveyItems());

        assertThat(savedQuestion.getId()).isEqualTo(questionId);
        assertThat(savedQuestion.getName()).isEqualTo(question.getName());
        assertThat(savedQuestion.getDescription()).isEqualTo(question.getDescription());
        assertThat(savedQuestion.getOptions()).containsAll(question.getOptions());
        assertThat(savedQuestion.getSurveyId()).isEqualTo(surveyId);
    }

    @Test
    @DisplayName("[FAILURE] 10개 이상의 설문항목을 가진 설문조사는 생성될 수 없다.")
    void failToCreateSurveyWhenSurveyHaveQuestionOverTen() {
        // given
        final Long surveyId = 1L;

        Survey survey = new Survey(surveyId, "설문조사이름", "설문조사설명");
        List<SurveyItem> surveyItems = List.of(
            new TextSurveyItem(1L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(2L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(3L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(4L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(5L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(6L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(7L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(8L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(9L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(10L, "설문조사이름", "설문항목설명", false),
            new TextSurveyItem(11L, "설문조사이름", "설문항목설명", false)
        );

        assertThatThrownBy(() -> survey.addSurveyItems(surveyItems))
            .isInstanceOf(CustomException.class)
            .extracting(e -> ((CustomException) e).getStatus())
            .isEqualTo(CustomResponseStatus.SURVEY_ITEM_SIZE_FULL);
    }

    @Test
    @DisplayName("[SUCCESS] 설문조사의 이름, 설명을 수정할 수 있다.")
    void updateSurveyNameAndDescription() {
        // given
        TextSurveyItem question = SurveyItemFixture.createTextSurveyItem();
        Survey survey = SurveyFixture.createSurvey(List.of(question));

        Survey savedSurvey = surveyService.createSurvey(survey);
        entityManager.flush();
        Long surveyId = savedSurvey.getId();

        SurveyUpdateRequest request = new SurveyUpdateRequest(
            "변경설문조사이름",
            "변경설문조사설명"
        );

        // when
        Survey updatedSurvey = surveyService.updateSurvey(surveyId, request);

        // then
        assertThat(updatedSurvey.getName()).isEqualTo(request.name());
        assertThat(updatedSurvey.getDescription()).isEqualTo(request.description());
    }

}