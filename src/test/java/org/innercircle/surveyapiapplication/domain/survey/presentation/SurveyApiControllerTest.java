package org.innercircle.surveyapiapplication.domain.survey.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.innercircle.surveyapiapplication.domain.survey.application.SurveyService;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.fixture.SurveyFixture;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyCreateRequest;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.SurveyInquiryResponse;
import org.innercircle.surveyapiapplication.domain.surveyItem.application.SurveyItemService;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.MultiChoiceSurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.domain.surveyItem.fixture.SurveyItemFixture;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemCreateRequest;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemInquiryResponse;
import org.innercircle.surveyapiapplication.domain.surveyItem.presentation.dto.SurveyItemUpdateRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SurveyApiController.class)
@ExtendWith(SpringExtension.class)
class SurveyApiControllerTest {

    @MockitoBean
    private SurveyService surveyService;

    @MockitoBean
    private SurveyItemService surveyItemService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String ROOT_PATH = "/api/v1/survey";

    @Test
    @DisplayName("[SUCCESS] 설문조사 단건을 조회한다.")
    void inquireSurvey() throws Exception {
        // given
        List<SurveyItem> surveyItems = List.of(SurveyItemFixture.createTextSurveyItem());
        Survey survey = SurveyFixture.createSurvey(surveyItems);

        // when
        when(
            surveyService.findById(survey.getId())
        ).thenReturn(survey);

        SurveyInquiryResponse surveyInquiryResponse = SurveyInquiryResponse.from(survey);
        SurveyItemInquiryResponse surveyItemInquiryResponse = surveyInquiryResponse.questionResponses().get(0);

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

                jsonPath("$.data.questionResponses[0].id").value(surveyItemInquiryResponse.id()),
                jsonPath("$.data.questionResponses[0].version").value(surveyItemInquiryResponse.version()),
                jsonPath("$.data.questionResponses[0].name").value(surveyItemInquiryResponse.name()),
                jsonPath("$.data.questionResponses[0].description").value(surveyItemInquiryResponse.description()),
                jsonPath("$.data.questionResponses[0].type").value(surveyItemInquiryResponse.type().name()),
                jsonPath("$.data.questionResponses[0].required").value(surveyItemInquiryResponse.required()),
                jsonPath("$.data.questionResponses[0].options").value(surveyItemInquiryResponse.options())
            );

        verify(surveyService, times(1)).findById(survey.getId());
    }

    @Test
    @DisplayName("[SUCCESS] 설문조사를 생성한다.")
    void createSurvey() throws Exception {
        // given
        SurveyItem surveyItem = SurveyItemFixture.createTextSurveyItem();
        List<SurveyItem> surveyItems = List.of(surveyItem);
        Survey survey = SurveyFixture.createSurvey(surveyItems);

        SurveyItemCreateRequest surveyItemCreateRequest = new SurveyItemCreateRequest(
            surveyItem.getName(),
            surveyItem.getDescription(),
            surveyItem.isRequired(),
            SurveyItemType.TEXT,
            null
        );
        SurveyCreateRequest surveyCreateRequest = new SurveyCreateRequest(
            survey.getName(),
            survey.getDescription(),
            List.of(surveyItemCreateRequest)
        );

        // when
        when(
            surveyService.createSurvey(surveyCreateRequest.toDomain())
        ).thenReturn(survey);

        SurveyInquiryResponse surveyInquiryResponse = SurveyInquiryResponse.from(survey);
        SurveyItemInquiryResponse surveyItemInquiryResponse = surveyInquiryResponse.questionResponses().get(0);

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

                jsonPath("$.data.questionResponses[0].id").value(surveyItemInquiryResponse.id()),
                jsonPath("$.data.questionResponses[0].version").value(surveyItemInquiryResponse.version()),
                jsonPath("$.data.questionResponses[0].name").value(surveyItemInquiryResponse.name()),
                jsonPath("$.data.questionResponses[0].description").value(surveyItemInquiryResponse.description()),
                jsonPath("$.data.questionResponses[0].type").value(surveyItemInquiryResponse.type().name()),
                jsonPath("$.data.questionResponses[0].required").value(surveyItemInquiryResponse.required()),
                jsonPath("$.data.questionResponses[0].options").value(surveyItemInquiryResponse.options())
            );

        verify(surveyService, times(1)).createSurvey(surveyCreateRequest.toDomain());
    }

    @Test
    @DisplayName("[SUCCESS] 설문항목 내 선택지가 있는 설문조사를 생성한다.")
    void createSurveyWithOptions() throws Exception {
        // given
        MultiChoiceSurveyItem question = SurveyItemFixture.createMultiChoiceSurveyItem();
        List<SurveyItem> surveyItems = List.of(question);
        Survey survey = SurveyFixture.createSurvey(surveyItems);

        SurveyItemCreateRequest surveyItemCreateRequest = new SurveyItemCreateRequest(
            question.getName(),
            question.getDescription(),
            question.isRequired(),
            SurveyItemType.TEXT,
            question.getOptions()
        );
        SurveyCreateRequest surveyCreateRequest = new SurveyCreateRequest(
            survey.getName(),
            survey.getDescription(),
            List.of(surveyItemCreateRequest)
        );

        // when
        when(
            surveyService.createSurvey(surveyCreateRequest.toDomain())
        ).thenReturn(survey);

        SurveyInquiryResponse surveyInquiryResponse = SurveyInquiryResponse.from(survey);
        SurveyItemInquiryResponse surveyItemInquiryResponse = surveyInquiryResponse.questionResponses().get(0);

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

                jsonPath("$.data.questionResponses[0].id").value(surveyItemInquiryResponse.id()),
                jsonPath("$.data.questionResponses[0].version").value(surveyItemInquiryResponse.version()),
                jsonPath("$.data.questionResponses[0].name").value(surveyItemInquiryResponse.name()),
                jsonPath("$.data.questionResponses[0].description").value(surveyItemInquiryResponse.description()),
                jsonPath("$.data.questionResponses[0].type").value(surveyItemInquiryResponse.type().name()),
                jsonPath("$.data.questionResponses[0].required").value(surveyItemInquiryResponse.required()),
                jsonPath("$.data.questionResponses[0].options").value(surveyItemInquiryResponse.options())
            );

        verify(surveyService, times(1)).createSurvey(surveyCreateRequest.toDomain());
    }

    @Test
    @DisplayName("[SUCCESS] 이미 존재하는 설문조사에 설문항목을 등록한다.")
    void createSingleSurveyItemInExistingSurvey() throws Exception {
        // given
        Long surveyId = 1L;
        SurveyItemCreateRequest request = new SurveyItemCreateRequest(
            "설문항목",
            "설문항목설명",
            false,
            SurveyItemType.TEXT,
            null
        );

        SurveyItem surveyItem = request.toDomain();
        surveyItem.setSurveyId(surveyId);

        // when
        when(
            surveyItemService.createQuestion(eq(surveyId), any(SurveyItem.class))
        ).thenReturn(surveyItem);

        SurveyItemInquiryResponse surveyItemInquiryResponse = SurveyItemInquiryResponse.from(surveyItem);

        // then
        this.mockMvc
            .perform(
                post(ROOT_PATH + "/{surveyId}/survey-item", surveyId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(CustomResponseStatus.SUCCESS.getCode()),
                jsonPath("$.message").value(CustomResponseStatus.SUCCESS.getMessage()),
                jsonPath("$.data.id").value(surveyItemInquiryResponse.id()),
                jsonPath("$.data.name").value(surveyItemInquiryResponse.name()),
                jsonPath("$.data.description").value(surveyItemInquiryResponse.description()),
                jsonPath("$.data.type").value(surveyItemInquiryResponse.type().name()),
                jsonPath("$.data.required").value(surveyItemInquiryResponse.required()),
                jsonPath("$.data.surveyId").value(surveyItemInquiryResponse.surveyId()),
                jsonPath("$.data.options").value(surveyItemInquiryResponse.options())
            );

        verify(surveyItemService, times(1)).createQuestion(eq(surveyId), any(SurveyItem.class));
    }

    @Test
    @DisplayName("[SUCCESS] 설문항목을 수정한다.")
    void updateSurveyItem() throws Exception {
        // given
        Long surveyId = 1L;
        SurveyItem surveyItem = SurveyItemFixture.createSingleChoiceSurveyItem();
        surveyItem.setSurveyId(surveyId);

        SurveyItemUpdateRequest request = new SurveyItemUpdateRequest(
            "변경설문항목이름",
            "변경설문항목설명",
            SurveyItemType.MULTI_CHOICE_ANSWER,
            true,
            List.of("설문항목옵션1", "설문항목옵션2", "설문항목옵션3", "설문항목옵션4", "설문항목옵션5")
        );
        surveyItem = surveyItem.update(request.name(), request.description(), request.type(), request.required(), request.options());

        // when
        when(
            surveyItemService.updateQuestion(eq(surveyId), eq(surveyItem.getId()), any(SurveyItemUpdateRequest.class))
        ).thenReturn(surveyItem);

        SurveyItemInquiryResponse surveyItemInquiryResponse = SurveyItemInquiryResponse.from(surveyItem);

        // then
        this.mockMvc
            .perform(
                patch(ROOT_PATH + "/{surveyId}/survey-item/{surveyItemId}", surveyId, surveyItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpectAll(
                status().isOk(),
                jsonPath("$.code").value(CustomResponseStatus.SUCCESS.getCode()),
                jsonPath("$.message").value(CustomResponseStatus.SUCCESS.getMessage()),
                jsonPath("$.data.id").value(surveyItemInquiryResponse.id()),
                jsonPath("$.data.version").value(surveyItemInquiryResponse.version()),
                jsonPath("$.data.name").value(request.name()),
                jsonPath("$.data.description").value(request.description()),
                jsonPath("$.data.type").value(request.type().name()),
                jsonPath("$.data.required").value(request.required()),
                jsonPath("$.data.options").value(request.options())
            );

        verify(surveyItemService, times(1)).updateQuestion(surveyId, surveyItem.getId(), request);
    }

}