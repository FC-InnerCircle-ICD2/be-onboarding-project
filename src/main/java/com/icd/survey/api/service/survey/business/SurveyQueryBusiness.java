package com.icd.survey.api.service.survey.business;

import com.icd.survey.api.entity.survey.ItemAnswer;
import com.icd.survey.api.entity.survey.ItemAnswerOption;
import com.icd.survey.api.entity.survey.Survey;
import com.icd.survey.api.entity.survey.SurveyItem;
import com.icd.survey.api.entity.survey.repository.AnswerOptionRepository;
import com.icd.survey.api.entity.survey.repository.ItemAnswerRepository;
import com.icd.survey.api.entity.survey.repository.SurveyItemRepository;
import com.icd.survey.api.entity.survey.repository.SurveyRepository;
import com.icd.survey.api.entity.survey.repository.query.SurveyQueryRepository;
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
    private final SurveyQueryRepository surveyQueryRepository;

    public Optional<List<ItemAnswer>> findItemAnswerList(Long itemSeq) {
        return itemAnswerRepository.findAllByItemSeq(itemSeq);
    }

    public Optional<Survey> findSurveyById(Long surveySeq) {
        return surveyRepository.findById(surveySeq);
    }

    public Optional<List<SurveyItem>> findItemAllBySurveySeq(Long surveySeq) {
        return surveyItemRepository.findAllBySurveySeq(surveySeq);
    }

    public Optional<ItemAnswerOption> findOptionByIdAndItemSeq(Long optionSeq, Long itemSeq) {
        return answerOptionRepository.findByOptionSeqAndItemSeq(optionSeq, itemSeq);
    }

    public Boolean isExistedUserSurvey(String surveyName, String ipAddress) {
        return surveyRepository.existsBySurveyNameAndIpAddress(surveyName, ipAddress);
    }

    public Optional<SurveyItem> findSurveyItemById(Long itemSeq) {
        return surveyItemRepository.findById(itemSeq);
    }


}
