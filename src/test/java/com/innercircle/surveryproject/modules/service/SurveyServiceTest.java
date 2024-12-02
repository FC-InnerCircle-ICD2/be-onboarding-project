package com.innercircle.surveryproject.modules.service;

import com.innercircle.surveryproject.global.utils.FileUtils;
import com.innercircle.surveryproject.infra.exceptions.InvalidInputException;
import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyDto;
import com.innercircle.surveryproject.modules.dto.SurveyUpdateDto;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class SurveyServiceTest {

    @Autowired
    private SurveyService surveyService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("설문조사 생성 시 설문조사 항목이 없는 경우 에러")
    void test_case_1() {
        // given // when
        SurveyCreateDto surveyCreateDto = new SurveyCreateDto();
        surveyCreateDto.setName("설문조사 테스트");
        surveyCreateDto.setDescription("설문조사 테스트입니다.");
        // then
        Assert.assertThrows(InvalidInputException.class, () -> surveyService.createSurvey(surveyCreateDto));
    }

    @Test
    @DisplayName("설문조사 수정 성공")
    void test_case_2() throws Exception {
        // given
        JsonNode jsonNode = FileUtils.readFileAsJson("testcase/success_survey.txt");
        // when
        SurveyCreateDto request = objectMapper.treeToValue(jsonNode, SurveyCreateDto.class);
        surveyService.createSurvey(request);
        // then
        JsonNode jsonNode1 = FileUtils.readFileAsJson("testcase/survey_update_success.txt");
        SurveyUpdateDto surveyUpdateDto = objectMapper.treeToValue(jsonNode1, SurveyUpdateDto.class);
        SurveyDto surveyDto = surveyService.updateSurvey(surveyUpdateDto);
        assertEquals("서비스 만족도 설문조사", surveyDto.getName());
    }

}