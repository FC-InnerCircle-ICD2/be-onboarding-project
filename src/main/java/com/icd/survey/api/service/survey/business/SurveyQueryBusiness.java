package com.icd.survey.api.service.survey.business;

import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.repository.survey.AnswerOptionRepository;
import com.icd.survey.api.repository.survey.ItemAnswerRepository;
import com.icd.survey.api.repository.survey.SurveyItemRepository;
import com.icd.survey.api.repository.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyQueryBusiness {
    private final SurveyRepository surveyRepository;
    private final SurveyItemRepository surveyItemRepository;
    private final ItemAnswerRepository itemAnswerRepository;
    private final AnswerOptionRepository answerOptionRepository;

    public Optional<Survey> findSurveyById(Long surveySeq) {
        return surveyRepository.findById(surveySeq);
    }

    public Optional<Survey> findUserSurvey(String surveyName, String userSeq) {
        return surveyRepository.findBySurveyNameAndIpAddress(surveyName, userSeq);
    }

    public Optional<List<SurveyItem>> findAllSurveyItem(Long surveySeq) {
        return surveyItemRepository.findAllBySurveySeq(surveySeq);
    }

    public Boolean isExistedUserSurvey(String surveyName, String ipAddress) {
        return surveyRepository.existsBySurveyNameAndIpAddress(surveyName, ipAddress);
    }

    public Optional<SurveyItem> findSurveyItemById(Long itemSeq) {
        return surveyItemRepository.findById(itemSeq);
    }

}
