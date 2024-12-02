package org.innercircle.surveyapiapplication.domain.survey.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;
import org.innercircle.surveyapiapplication.domain.question.fixture.QuestionFixture;
import org.innercircle.surveyapiapplication.domain.question.presentation.dto.QuestionInquiryResponse;
import org.innercircle.surveyapiapplication.domain.survey.application.SurveyService;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.fixture.SurveyFixture;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.QuestionCreateRequest;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyCreateRequest;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyInquiryResponse;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SurveyApiController.class)
@ExtendWith(SpringExtension.class)
class SurveyApiControllerTest {

    @MockitoBean
    private SurveyService surveyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String ROOT_PATH = "/api/v1/survey";

    @Test
    @DisplayName("[SUCCESS] 설문조사 단건을 조회한다.")
    void inquireSurvey() throws Exception {
        // given
        List<Question> questions = List.of(QuestionFixture.createShortQuestion());
        Survey survey = SurveyFixture.createSurvey(questions);

        // when
        when(
            surveyService.findById(survey.getId())
        ).thenReturn(survey);

        SurveyInquiryResponse surveyInquiryResponse = SurveyInquiryResponse.from(survey);
        QuestionInquiryResponse questionInquiryResponse = surveyInquiryResponse.questionResponses().get(0);

        // then
        this.mockMvc
            .perform(
                get(ROOT_PATH + "/{surveyId}", survey.getId())
            )
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(CustomResponseStatus.SUCCESS.getCode()),
                jsonPath("$.message").value(CustomResponseStatus.SUCCESS.getMessage()),

                jsonPath("$.data.id").value(surveyInquiryResponse.id()),
                jsonPath("$.data.name").value(surveyInquiryResponse.name()),
                jsonPath("$.data.description").value(surveyInquiryResponse.description()),

                jsonPath("$.data.questionResponses[0].id").value(questionInquiryResponse.id()),
                jsonPath("$.data.questionResponses[0].name").value(questionInquiryResponse.name()),
                jsonPath("$.data.questionResponses[0].description").value(questionInquiryResponse.description()),
                jsonPath("$.data.questionResponses[0].type").value(questionInquiryResponse.type().name()),
                jsonPath("$.data.questionResponses[0].required").value(questionInquiryResponse.required())
            );

        verify(surveyService, times(1)).findById(survey.getId());
    }

    @Test
    @DisplayName("[SUCCESS] 설문조사를 생성한다.")
    void createSurvey() throws Exception {
        // given
        Question shortQuestion = QuestionFixture.createShortQuestion();
        List<Question> questions = List.of(shortQuestion);
        Survey survey = SurveyFixture.createSurvey(questions);

        QuestionCreateRequest questionCreateRequest = new QuestionCreateRequest(
            shortQuestion.getName(),
            shortQuestion.getDescription(),
            shortQuestion.isRequired(),
            QuestionType.SHORT_ANSWER,
            null
        );
        SurveyCreateRequest surveyCreateRequest = new SurveyCreateRequest(
            survey.getName(),
            survey.getDescription(),
            List.of(questionCreateRequest)
        );

        // when
        when(
            surveyService.createSurvey(surveyCreateRequest.toDomain())
        ).thenReturn(survey);

        SurveyInquiryResponse surveyInquiryResponse = SurveyInquiryResponse.from(survey);
        QuestionInquiryResponse questionInquiryResponse = surveyInquiryResponse.questionResponses().get(0);

        // then
        this.mockMvc
            .perform(
                post(ROOT_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(surveyCreateRequest))
            )
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(CustomResponseStatus.SUCCESS.getCode()),
                jsonPath("$.message").value(CustomResponseStatus.SUCCESS.getMessage()),

                jsonPath("$.data.id").value(surveyInquiryResponse.id()),
                jsonPath("$.data.name").value(surveyInquiryResponse.name()),
                jsonPath("$.data.description").value(surveyInquiryResponse.description()),

                jsonPath("$.data.questionResponses[0].id").value(questionInquiryResponse.id()),
                jsonPath("$.data.questionResponses[0].name").value(questionInquiryResponse.name()),
                jsonPath("$.data.questionResponses[0].description").value(questionInquiryResponse.description()),
                jsonPath("$.data.questionResponses[0].type").value(questionInquiryResponse.type().name()),
                jsonPath("$.data.questionResponses[0].required").value(questionInquiryResponse.required())
            );

        verify(surveyService, times(1)).createSurvey(surveyCreateRequest.toDomain());
    }

}