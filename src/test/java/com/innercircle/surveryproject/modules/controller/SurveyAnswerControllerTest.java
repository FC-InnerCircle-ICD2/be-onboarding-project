package com.innercircle.surveryproject.modules.controller;

import com.innercircle.surveryproject.global.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/database/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SurveyAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("설문조사 응답 제출 시 필수값 입력 안한 경우 에러발생")
    void test_case_1() throws Exception {
        // given
        String request = FileUtils.readFileAsString("testcase/survey_answer_testcase1.txt");
        // when // then
        mockMvc.perform(post("/api/survey-answer").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(
                        status().isBadRequest())
                .andExpect(jsonPath("$.message").value("필수 항목을 입력해주세요."));
    }

}