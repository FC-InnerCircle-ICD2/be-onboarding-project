package com.innercircle.surveryproject.modules.service;

import com.innercircle.surveryproject.infra.exceptions.InvalidInputException;
import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SurveyServiceTest {

    @Autowired
    private SurveyService surveyService;

    @Test
    @DisplayName("설문조사 생성 시 설문조사 항목이 없는 경우 에러")
    void test_case_1() {
        // given
        // when
        SurveyCreateDto surveyCreateDto = new SurveyCreateDto();
        surveyCreateDto.setName("설문조사 테스트");
        surveyCreateDto.setDescription("설문조사 테스트입니다.");
        // then
        Assert.assertThrows(InvalidInputException.class, () -> surveyService.createSurvey(surveyCreateDto));
    }

}