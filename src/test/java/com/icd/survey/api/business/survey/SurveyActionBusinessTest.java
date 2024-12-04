package com.icd.survey.api.business.survey;

import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.dto.SurveyDto;
import com.icd.survey.api.repository.survey.AnswerOptionRepository;
import com.icd.survey.api.repository.survey.ItemAnswerRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
import com.icd.survey.api.repository.survey.query.SurveyQueryRepository;
import com.icd.survey.api.service.survey.business.SurveyActionBusiness;
import com.icd.survey.api.service.survey.business.SurveyQueryBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SurveyActionBusinessTest {
    @InjectMocks
    SurveyActionBusiness surveyActionBusiness;

    @Mock
    SurveyRepository surveyRepository;
    @Mock
    SurveyItemRepository surveyItemRepository;
    @Mock
    ItemAnswerRepository itemAnswerRepository;
    @Mock
    AnswerOptionRepository answerOptionRepository;
    @Mock
    SurveyQueryBusiness surveyQueryBusiness;
    @Mock
    SurveyQueryRepository surveyQueryRepository;

    private final String IP_ADDRESS = "0.0.0.1";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("inject dependencies test")
    void contextLoads() {
        assertThat(this).isNotNull();
    }

    @Test
    @DisplayName("설문조사 저장")
    void saveSurvey() {
        // given
        SurveyDto surveyDto =
                SurveyDto
                        .builder()
                        .surveyName("survey name")
                        .surveyDescription("survey description")
                        .ipAddress(IP_ADDRESS)
                        .build();
        //when
        Survey requestSurvey = Survey.createSurveyRequest(surveyDto);
        when(surveyRepository.save(any())).thenReturn(requestSurvey);
        Survey survey = surveyActionBusiness.saveSurvey(surveyDto);
        //then
        assertThat(survey).isEqualTo(requestSurvey);
    }

}
