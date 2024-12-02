package com.innercircle.surveryproject.modules.controller;

import com.innercircle.surveryproject.global.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("설문조사 등록 시 설문조사 항목이 없는 경우 에러 발생")
    void test_case_1() throws Exception {
        // given
        String request = FileUtils.readFileAsString("testcase/fail_survey.txt");
        // when
        // then
        mockMvc.perform(post("/api/survey").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(
                status().isBadRequest())
            .andExpect(jsonPath("$.message").value("설문조사 항목은 1~10개까지 등록가능합니다."));
    }

    @Test
    @DisplayName("설문조사 등록 성공")
    void test_case_2() throws Exception {
        // given
        String request = FileUtils.readFileAsString("testcase/success_survey.txt");
        // then

        mockMvc.perform(post("/api/survey").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(
                status().isCreated())
            .andExpect(jsonPath("$.message").value("설문조사 등록에 성공하였습니다."));

    }

}