package net.gentledot.survey.service;

import net.gentledot.survey.dto.request.SurveyCreateRequest;
import net.gentledot.survey.dto.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.dto.response.SurveyCreateResponse;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
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

    @BeforeEach
    void setUp() {
        surveyRequest = SurveyCreateRequest.builder()
                .name("test")
                .description("survey description")
                .questions(List.of(
                        SurveyQuestionRequest.builder()
                                .itemName("question1")
                                .itemDescription("question1 description")
                                .itemType(SurveyItemType.MULTI_SELECT)
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
        Assertions.assertThat(createdSurvey.surveyId()).isNotBlank();
        Assertions.assertThat(createdSurvey.createdAt()).isBefore(LocalDateTime.now());
    }
}