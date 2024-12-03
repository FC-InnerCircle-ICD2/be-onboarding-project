package com.metsakurr.beonboardingproject.domain.survey.service;

import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.metsakurr.beonboardingproject.domain.survey.repository.SurveyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
    // 테스트코드가 하나의 문서가 되게 하려면?
    // 1. 설문이름과 설명이 없는 설문조사는 생성할 수 없다.
    // 2. 설문 받을 항목은 1개애 이상, 10개 이하여야 한다.
    // 3. 설문 받을 항목이 없는 설문조사는 생성할 수 없다.
    // 4. 항목이름과 설명이 없는 설문항목은 생성할 수 없다.
    // 5. 단일 선택리스트, 다중 선택 리스트의 경우 선택할 수 있는 후보 값이 필요하다.
    //

    @InjectMocks
    private SurveyService surveyService;

    @Mock
    private SurveyRepository surveyRepository;

    @Test
    @DisplayName("설문조사를 저장한다.")
    public void createSurvey() {
        // given
//        SurveyRequest surveyRequest = new SurveyRequest();
//        Survey survey = surveyRequest.toEntity();
//        BDDMockito.given(surveyRepository.save(survey)).willReturn(survey);
//
//        // when
//        surveyService.create(surveyRequest);
//
//        // then
//        assertThat(survey.getIdx()).isEqualTo(1L);
    }
}