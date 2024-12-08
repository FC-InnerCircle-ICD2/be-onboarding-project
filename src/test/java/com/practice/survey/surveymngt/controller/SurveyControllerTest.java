package com.practice.survey.surveymngt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.survey.surveyItem.model.dto.SurveyItemSaveRequestDto;
import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyItem.model.enums.InputType;
import com.practice.survey.surveyItemOption.model.dto.SurveyItemOptionSaveRequestDto;
import com.practice.survey.surveyVersion.model.entity.SurveyVersion;
import com.practice.survey.surveymngt.model.dto.SurveyRequestDto;
import com.practice.survey.surveymngt.model.entity.Survey;
import com.practice.survey.surveyItemOption.repository.SurveyItemOptionRepository;
import com.practice.survey.surveyItem.repository.SurveyItemRepository;
import com.practice.survey.surveymngt.repository.SurveyRepository;
import com.practice.survey.surveyVersion.repository.SurveyVersionRepository;
import com.practice.survey.surveymngt.service.SurveyService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyItemRepository surveyItemRepository;

    @Autowired
    private SurveyItemOptionRepository surveyItemOptionRepository;

    @Autowired
    private SurveyVersionRepository surveyVersionRepository;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private String local_address = "http://localhost";
    private String path = ":" + port + "/api/v1/survey";
    private String url;
    private String local_url;
    private String deployed_url;
    private String params;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @DisplayName("[Controller] 설문조사 생성 테스트")
    @Test
    @Transactional
    public void createSurveyTest() throws Exception{

        // given
        url = "/createSurvey";
        local_url = local_address + path + url;

        List<SurveyItemOptionSaveRequestDto> options = new ArrayList<>();

        SurveyItemOptionSaveRequestDto surveyItemOptionSaveRequestDto1 = SurveyItemOptionSaveRequestDto.builder()
                .optionText("선택지1")
                .build();

        SurveyItemOptionSaveRequestDto surveyItemOptionSaveRequestDto2 = SurveyItemOptionSaveRequestDto.builder()
                .optionText("선택지2")
                .build();

        options.add(surveyItemOptionSaveRequestDto1);
        options.add(surveyItemOptionSaveRequestDto2);

        List<SurveyItemSaveRequestDto> surveyItems = new ArrayList<>();

        SurveyItemSaveRequestDto surveyItemSaveRequestDto1 = SurveyItemSaveRequestDto.builder()
                .name("질문1")
                .description("설명1")
                .inputType(InputType.SHORT_TEXT)
                .isRequired(true)
                .build();

        SurveyItemSaveRequestDto surveyItemSaveRequestDto2 = SurveyItemSaveRequestDto.builder()
                .name("질문2")
                .description("설명2")
                .inputType(InputType.SINGLE_CHOICE)
                .isRequired(true)
                .options(options)
                .build();

        surveyItems.add(surveyItemSaveRequestDto1);
        surveyItems.add(surveyItemSaveRequestDto2);

        SurveyRequestDto surveyRequestDto = SurveyRequestDto.builder()
                .name("설문조사 이름1")
                .description("설문조사 설명1")
                .surveyItems(surveyItems)
                .build();

        // ObjectMapper 추가
        ObjectMapper objectMapper = new ObjectMapper();
        String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(surveyRequestDto);
        System.out.println("Formatted JSON Body:\n" + formattedJson); // 포맷팅된 JSON 출력

        // when
        mvc.perform(post(local_url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")  // 추가된 인코딩 설정
                .content(objectMapper.writeValueAsString(surveyRequestDto)))
                .andDo(print())  // 요청과 응답 출력
                .andExpect(status().isOk());

        // then
        // 설문조사 이름 매칭 확인
        Survey foundSurvey = surveyRepository.findByName("설문조사 이름1");
        assertThat(foundSurvey).isNotNull();
        assertThat(foundSurvey.getName()).isEqualTo("설문조사 이름1");
        // 설문조사 아이템의 필수 여부가 true로 저장되었는지 확인
        SurveyVersion surveyVersion = surveyVersionRepository.findBySurvey(foundSurvey);
        List<SurveyItem> surveyItem = surveyItemRepository.findByVersion(surveyVersion);
        assertThat(surveyItem.get(0).getIsRequired()).isTrue();
        assertThat(surveyItem.get(1).getIsRequired()).isTrue();
    }

    @DisplayName("[Controller] 설문조사 수정 테스트")
    @Test
    @Transactional
    public void updateSurveyTest() throws Exception{

        // given
        url = "/updateSurvey";
        local_url = local_address + path + url;
        createSurveyTest();

        List<SurveyItemOptionSaveRequestDto> options = new ArrayList<>();

        SurveyItemOptionSaveRequestDto surveyItemOptionSaveRequestDto1 = SurveyItemOptionSaveRequestDto.builder()
                .optionText("선택지1")
                .build();

        SurveyItemOptionSaveRequestDto surveyItemOptionSaveRequestDto2 = SurveyItemOptionSaveRequestDto.builder()
                .optionText("선택지2")
                .build();

        options.add(surveyItemOptionSaveRequestDto1);
        options.add(surveyItemOptionSaveRequestDto2);

        List<SurveyItemSaveRequestDto> surveyItems = new ArrayList<>();

        SurveyItemSaveRequestDto surveyItemSaveRequestDto1 = SurveyItemSaveRequestDto.builder()
                .name("질문1")
                .description("설명1")
                .inputType(InputType.SHORT_TEXT)
                .isRequired(true)
                .build();

        SurveyItemSaveRequestDto surveyItemSaveRequestDto2 = SurveyItemSaveRequestDto.builder()
                .name("질문2")
                .description("설명2")
                .inputType(InputType.SINGLE_CHOICE)
                .isRequired(true)
                .options(options)
                .build();

        surveyItems.add(surveyItemSaveRequestDto1);
        surveyItems.add(surveyItemSaveRequestDto2);

        SurveyRequestDto surveyRequestDto = SurveyRequestDto.builder()
                .name("설문조사 이름1")
                .description("설문조사 수정 설명1")
                .surveyItems(surveyItems)
                .build();

        // ObjectMapper 추가
        ObjectMapper objectMapper = new ObjectMapper();
        String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(surveyRequestDto);
        System.out.println("Formatted JSON Body:\n" + formattedJson); // 포맷팅된 JSON 출력

        // when
        mvc.perform(post(local_url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")  // 추가된 인코딩 설정
                        .content(objectMapper.writeValueAsString(surveyRequestDto)))
                .andDo(print())  // 요청과 응답 출력
                .andExpect(status().isOk());

        // then
        Survey foundSurvey = surveyRepository.findByName("설문조사 이름1");
        assertThat(foundSurvey).isNotNull();
        assertThat(foundSurvey.getName()).isEqualTo("설문조사 이름1");

    }

}
