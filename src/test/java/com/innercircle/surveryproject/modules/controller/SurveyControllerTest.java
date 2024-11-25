package com.innercircle.surveryproject.modules.controller;

import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyItemDto;
import com.innercircle.surveryproject.modules.enums.ItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("설문조사 등록 시 설문조사 항목이 없는 경우 에러 발생")
    void test_case_1() throws Exception {
        // given
        SurveyCreateDto surveyCreateDto = new SurveyCreateDto();
        surveyCreateDto.setName("테스트");
        surveyCreateDto.setDescription("테스트");
        // when
        // then
        mockMvc.perform(post("/api/survey").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(surveyCreateDto))).andExpect(
                status().isBadRequest())
            .andExpect(jsonPath("$.message").value("설문조사 항목은 1~10개까지 등록가능합니다."));
    }

    @Test
    @DisplayName("설문조사 등록 성공")
    void test_case_2() throws Exception {
        // given
        SurveyCreateDto surveyCreateDto = new SurveyCreateDto();
        surveyCreateDto.setName("테스트");
        surveyCreateDto.setDescription("테스트");
        // when
        SurveyItemDto surveyItemDto = new SurveyItemDto();
        surveyItemDto.setName("테스트");
        surveyItemDto.setDescription("테스트");
        surveyItemDto.setItemType(ItemType.SHORT_TEXT);
        surveyItemDto.setRequired(true);

        surveyCreateDto.setSurveyItemDtoList(List.of(surveyItemDto));
        // then

        mockMvc.perform(post("/api/survey").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(surveyCreateDto))).andExpect(
                status().isCreated())
            .andExpect(jsonPath("$.message").value("성공하였습니다."));

    }

}