package com.innercircle.surveryproject.modules.repository;

import com.innercircle.surveryproject.global.utils.FileUtils;
import com.innercircle.surveryproject.modules.entity.Survey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SurveyRepositoryTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Test
    @DisplayName("설문조사 데이터 저장 테스트")
    void test_case_1() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode testcase = FileUtils.readFileAsJson("testcase/success_survey.txt");
        Survey survey = objectMapper.treeToValue(testcase, Survey.class);
        surveyRepository.save(survey);

        Optional<Survey> optionalSurvey = surveyRepository.findByName(survey.getName());
        if (optionalSurvey.isEmpty()) {
            throw new IllegalStateException("일치하는 설문조사가 없습니다.");
        }
        Survey result = optionalSurvey.get();
        assertEquals(survey.getName(), result.getName());
        assertEquals(survey.getDescription(), result.getDescription());
    }

}