package com.innercircle.surveryproject.modules.service;

import com.innercircle.surveryproject.global.utils.FileUtils;
import com.innercircle.surveryproject.infra.exceptions.InvalidInputException;
import com.innercircle.surveryproject.modules.dto.SurveyAnswerCreateDto;
import com.innercircle.surveryproject.modules.entity.Survey;
import com.innercircle.surveryproject.modules.repository.SurveyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
class SurveyAnswerServiceTest {

    @Autowired
    private SurveyAnswerService surveyAnswerService;

    @Autowired
    private SurveyRepository surveyRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("설문조사 응답 생성 시 필수 항목이 누락된 경우 에러")
    void testSurveyAnswer() throws IOException {
        List<Survey> all = surveyRepository.findAll();
        System.out.println(all);
        JsonNode jsonNode = FileUtils.readFileAsJson("testcase/survey_answer_testcase1.txt");
        SurveyAnswerCreateDto surveyAnswerCreateDto = objectMapper.treeToValue(jsonNode, SurveyAnswerCreateDto.class);
        String message = assertThrows(InvalidInputException.class, () -> surveyAnswerService.createSurveyAnswer(surveyAnswerCreateDto)).getMessage();
        assertEquals("필수 항목을 입력해주세요.", message);
    }

}