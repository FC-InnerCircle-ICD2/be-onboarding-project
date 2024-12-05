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

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
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