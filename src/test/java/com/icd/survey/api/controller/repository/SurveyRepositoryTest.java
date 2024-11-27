package com.icd.survey.api.controller.repository;

import com.icd.survey.api.repository.survey.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SurveyRepositoryTest {
    @Autowired
    SurveyRepository surveyRepository;

}
