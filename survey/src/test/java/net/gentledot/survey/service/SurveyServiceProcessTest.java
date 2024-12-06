package net.gentledot.survey.service;

import jakarta.transaction.Transactional;
import net.gentledot.survey.domain.enums.ItemRequired;
import net.gentledot.survey.domain.enums.SurveyItemType;
import net.gentledot.survey.domain.enums.UpdateType;
import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveyCreationException;
import net.gentledot.survey.repository.jpa.SurveyJpaQuestionRepository;
import net.gentledot.survey.repository.jpa.SurveyJpaRepository;
import net.gentledot.survey.service.in.model.request.SurveyCreateRequest;
import net.gentledot.survey.service.in.model.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.service.in.model.request.SurveyQuestionRequest;
import net.gentledot.survey.service.in.model.request.SurveyUpdateRequest;
import net.gentledot.survey.service.in.model.response.SurveyCreateResponse;
import net.gentledot.survey.service.in.model.response.SurveyUpdateResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class SurveyServiceProcessTest {

    private SurveyCreateRequest surveyRequest;

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveyJpaRepository surveyJpaRepository;

    @Autowired
    SurveyJpaQuestionRepository questionRepository;

    @Autowired
    private SurveyJpaQuestionRepository surveyQuestionRepository;

    @BeforeEach
    void setUp() {
        surveyRequest = SurveyCreateRequest.builder()
                .name("test")
                .description("survey description")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .question("question1")
                                .description("question1 description")
                                .type(SurveyItemType.MULTI_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("option1"),
                                        new SurveyQuestionOptionRequest("option2")
                                )).build()
                )).build();
    }


    @Test
    void createProcessTest() {
        SurveyCreateResponse createdSurvey = surveyService.createSurvey(surveyRequest);
        Assertions.assertThat(createdSurvey.getSurveyId()).isNotBlank();
        Assertions.assertThat(createdSurvey.getCreatedAt()).isBefore(LocalDateTime.now());
    }

    @Transactional
    @Test
    void updateProcessTest() {
        // a
        SurveyCreateResponse createdSurvey = surveyService.createSurvey(surveyRequest);
        String surveyId = createdSurvey.getSurveyId();

        Survey survey = surveyJpaRepository.findById(surveyId).get();
        List<SurveyQuestion> questions = survey.getQuestions();
        SurveyQuestion beforeQuestion = surveyQuestionRepository.findById(questions.get(0).getId()).get();

        SurveyUpdateRequest updateRequest = SurveyUpdateRequest.builder()
                .id(surveyId)
                .name("test changed")
                .description("changed description")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .updateType(UpdateType.MODIFY)
                                .questionId(beforeQuestion.getId())
                                .question("changed1")
                                .description("changed1 description")
                                .type(SurveyItemType.MULTI_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("changed option1"),
                                        new SurveyQuestionOptionRequest("changed option2")
                                )).build(),
                        SurveyQuestionRequest.builder()
                                .question("added1")
                                .description("add1 description")
                                .type(SurveyItemType.TEXT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("insert one")
                                )).build()
                )).build();

        // a
        SurveyUpdateResponse surveyUpdateResponse = surveyService.updateSurvey(updateRequest);

        Survey updatedSurvey = surveyJpaRepository.findById(surveyId).get();
        List<SurveyQuestion> updatedQuestions = updatedSurvey.getQuestions();


        // a
        Assertions.assertThat(surveyUpdateResponse.getSurveyId()).isEqualTo(surveyId);
        Assertions.assertThat(surveyUpdateResponse.getUpdatedAt()).isBefore(LocalDateTime.now());

        Assertions.assertThat(updatedQuestions).hasSize(2);
        SurveyQuestion updatedQuestion = updatedQuestions.get(0);
        Assertions.assertThat(updatedQuestion.getSurvey()).isEqualTo(updatedSurvey);
        Assertions.assertThat(updatedQuestion.getItemName()).isEqualTo("changed1");
        Assertions.assertThat(updatedQuestion.getItemDescription()).isEqualTo("changed1 description");
        Assertions.assertThat(updatedQuestion.getOptions()).hasSize(2);
        Assertions.assertThat(updatedQuestion.getOptions().get(0).getOptionText()).isEqualTo("changed option1");
        Assertions.assertThat(updatedQuestion.getOptions().get(1).getOptionText()).isEqualTo("changed option2");

        Assertions.assertThat(beforeQuestion.getSurvey()).isNotNull();
        Assertions.assertThat(beforeQuestion.getId()).isEqualTo(updatedQuestion.getId());
    }

    @Transactional
    @Test
    void failTest_updateProcessWithDuplicateQuestionIdTest() {
        // a
        SurveyCreateResponse createdSurvey = surveyService.createSurvey(surveyRequest);
        String surveyId = createdSurvey.getSurveyId();

        Survey survey = surveyJpaRepository.findById(surveyId).get();
        List<SurveyQuestion> questions = survey.getQuestions();
        SurveyQuestion beforeQuestion = surveyQuestionRepository.findById(questions.get(0).getId()).get();

        SurveyUpdateRequest updateRequest = SurveyUpdateRequest.builder()
                .id(surveyId)
                .name("test changed")
                .description("changed description")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .questionId(beforeQuestion.getId())
                                .question("changed1")
                                .description("changed1 description")
                                .type(SurveyItemType.MULTI_SELECT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("changed option1"),
                                        new SurveyQuestionOptionRequest("changed option2")
                                )).build(),
                        SurveyQuestionRequest.builder()
                                .questionId(beforeQuestion.getId())
                                .question("added1")
                                .description("add1 description")
                                .type(SurveyItemType.TEXT)
                                .required(ItemRequired.REQUIRED)
                                .options(List.of(
                                        new SurveyQuestionOptionRequest("insert one")
                                )).build()
                )).build();

        // a
        Assertions.assertThatThrownBy(() -> surveyService.updateSurvey(updateRequest))
                .isInstanceOf(SurveyCreationException.class)
                .hasMessageContaining(ServiceError.CREATION_DUPLICATE_QUESTIONS.getMessage());
    }
}