package com.icd.survey.api.business.survey;

import com.icd.survey.api.repository.survey.AnswerOptionRepository;
import com.icd.survey.api.repository.survey.ItemAnswerRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
import com.icd.survey.api.repository.survey.query.SurveyQueryRepository;
import com.icd.survey.api.service.survey.business.SurveyQueryBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(SpringExtension.class)
public class SurveyQueryBusinessTest {

    /*
    * 본 클래스는 repository test 로 전부 대체 가능.
    */
    @InjectMocks
    SurveyQueryBusiness surveyQueryBusiness;
    @Mock
    SurveyRepository surveyRepository;
    @Mock
    SurveyItemRepository surveyItemRepository;
    @Mock
    ItemAnswerRepository itemAnswerRepository;
    @Mock
    AnswerOptionRepository answerOptionRepository;
    @Mock
    SurveyQueryRepository surveyQueryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("inject dependencies test")
    void contextLoads() {
        assertThat(this).isNotNull();
    }
}
