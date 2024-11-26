package com.icd.survey.api.controller.repository;

import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.repository.survey.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class SurveyRepositoryTest {
    @Autowired
    SurveyRepository surveyRepository;

    @BeforeEach
    @DisplayName("Before Each! save the entity")
    void makeSurveyEntity() {
        Survey survey = Survey
                .builder()
                .surveyName("test name")
                .surveyDescription("test descriptoon")
                .ipAddress("127.0.0.1")
                .build();

        surveyRepository.save(survey);
    }

    @Test
    @DisplayName("find by surveyName and ipAddress test")
    void findBySurveyNameAndIpAddressTest() {

        Survey survey = surveyRepository.findBySurveyNameAndIpAddress("test name", "127.0.0.1")
                .orElse(null);

        assertNotNull(survey);
    }

}
