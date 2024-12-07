package org.innercircle.surveyapiapplication.domain.surveyItem.application;

import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.domain.surveyItem.fixture.SurveyItemFixture;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.fixture.SurveyFixture;
import org.innercircle.surveyapiapplication.domain.survey.infrastructure.SurveyRepository;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemUpdateRequest;
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
class SurveyItemServiceTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyItemService surveyItemService;

    @Test
    @DisplayName("[SUCCESS] 설문조사에 설문항목을 등록할 수 있다")
    void createSingleQuestion() {
        // given
        SurveyItem surveyItem = SurveyItemFixture.createTextSurveyItem();
        Survey survey = SurveyFixture.createSurvey(List.of(surveyItem));

        // when
        Survey savedSurvey = surveyRepository.save(survey);
        SurveyItem targetSurveyItem = SurveyItemFixture.createTextSurveyItem();
        surveyItemService.createQuestion(savedSurvey.getId(), targetSurveyItem);

        SurveyItem savedSurveyItem = surveyItemService.findByIdAndVersion(survey.getId(), targetSurveyItem.getId(), targetSurveyItem.getVersion());

        // then
        assertThat(savedSurveyItem.getId()).isEqualTo(targetSurveyItem.getId());
        assertThat(savedSurveyItem.getVersion()).isEqualTo(targetSurveyItem.getVersion());
        assertThat(savedSurveyItem.getName()).isEqualTo(targetSurveyItem.getName());
        assertThat(savedSurveyItem.getDescription()).isEqualTo(targetSurveyItem.getDescription());
        assertThat(savedSurveyItem.getType()).isEqualTo(targetSurveyItem.getType());
        assertThat(savedSurveyItem.getSurveyId()).isEqualTo(targetSurveyItem.getSurveyId());
    }

    @Test
    @DisplayName("[FAILURE] 설문조사에 10개의 설문 항목이 있는경우 설문항목 추가 시 예외가 발생한다.")
    void failToCreateSingleQuestionWhenSurveyAlreadyHaveTenQuestions() {
        // given
        List<SurveyItem> surveyItems = List.of(
            SurveyItemFixture.createTextSurveyItem(1L),
            SurveyItemFixture.createTextSurveyItem(2L),
            SurveyItemFixture.createTextSurveyItem(3L),
            SurveyItemFixture.createTextSurveyItem(4L),
            SurveyItemFixture.createTextSurveyItem(5L),
            SurveyItemFixture.createTextSurveyItem(6L),
            SurveyItemFixture.createTextSurveyItem(7L),
            SurveyItemFixture.createTextSurveyItem(8L),
            SurveyItemFixture.createTextSurveyItem(9L),
            SurveyItemFixture.createTextSurveyItem(10L)
        );

        Survey survey = SurveyFixture.createSurvey(surveyItems);
        Survey savedSurvey = surveyRepository.save(survey);

        // when & then
        SurveyItem targetSurveyItem = SurveyItemFixture.createTextSurveyItem(11L);

        assertThatThrownBy(() -> surveyItemService.createQuestion(savedSurvey.getId(), targetSurveyItem))
            .isInstanceOf(CustomException.class)
            .extracting(e -> ((CustomException) e).getStatus())
            .isEqualTo(CustomResponseStatus.QUESTION_SIZE_FULL);
    }

    @Test
    @DisplayName("[SUCCESS] 설문항목을 수정할 수 있다.")
    void updateQuestion() {
        // given
        SurveyItem surveyItem = SurveyItemFixture.createTextSurveyItem();
        Survey survey = SurveyFixture.createSurvey(List.of(surveyItem));


        Survey savedSurvey = surveyRepository.save(survey);
        SurveyItem targetSurveyItem = SurveyItemFixture.createTextSurveyItem();
        surveyItemService.createQuestion(savedSurvey.getId(), targetSurveyItem);

        SurveyItemUpdateRequest request = new SurveyItemUpdateRequest(
            "변경설문항목이름",
            "변경설문항목설명",
            SurveyItemType.MULTI_CHOICE_ANSWER,
            true,
            List.of("설문항목옵션1", "설문항목옵션2", "설문항목옵션3", "설문항목옵션4", "설문항목옵션5")
        );

        // when
        MultiChoiceSurveyItem updatedQuestion = (MultiChoiceSurveyItem) surveyItemService.updateQuestion(survey.getId(), targetSurveyItem.getId(), request);

        // then
        assertThat(updatedQuestion.getId()).isEqualTo(targetSurveyItem.getId());
        assertThat(updatedQuestion.getVersion()).isEqualTo(targetSurveyItem.getVersion() + 1);
        assertThat(updatedQuestion.getSurveyId()).isEqualTo(targetSurveyItem.getSurveyId());

        assertThat(updatedQuestion.getName()).isEqualTo(request.name());
        assertThat(updatedQuestion.getDescription()).isEqualTo(request.description());
        assertThat(updatedQuestion.getType()).isEqualTo(request.type());
        assertThat(updatedQuestion.isRequired()).isEqualTo(request.required());
        assertThat(updatedQuestion.getOptions()).containsAll(request.options());
    }

}