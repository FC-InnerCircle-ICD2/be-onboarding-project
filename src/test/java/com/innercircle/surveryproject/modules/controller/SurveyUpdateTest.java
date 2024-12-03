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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class SurveyUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = "/database/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("설문조사 수정 성공")
    void test_case_4() throws Exception {
        // given
        String updateRequest = FileUtils.readFileAsString("testcase/survey_update_success.txt");
        // when // then
        ResultActions result = mockMvc.perform(put("/api/survey")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(updateRequest));

        // 응답 검증
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("설문조사 수정에 성공하였습니다."));
    }

    @Test
    @DisplayName("설문조사 수정 실패")
    void test_case_3() throws Exception {
        // given
        String request = FileUtils.readFileAsString("testcase/survey_update_success.txt");
        // when // then
        ResultActions result = mockMvc.perform(put("/api/survey")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(request));

        // 응답 검증
        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("일치하는 설문조사를 찾을 수 없습니다."));
    }

}
