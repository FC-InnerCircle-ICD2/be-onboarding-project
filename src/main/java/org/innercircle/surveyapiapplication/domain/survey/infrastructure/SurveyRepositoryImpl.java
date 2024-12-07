package org.innercircle.surveyapiapplication.domain.survey.infrastructure;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.infrastructure.SurveyItemRepository;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.entity.SurveyEntity;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SurveyRepositoryImpl implements SurveyRepository {

    private final SurveyJpaRepository surveyJpaRepository;
    private final SurveyItemRepository surveyItemRepository;

    @Override
    @Transactional
    public Survey save(Survey survey) {
        SurveyEntity savedSurveyEntity = surveyJpaRepository.save(SurveyEntity.from(survey));
        List<SurveyItem> surveyItems = new ArrayList<>();
        for (SurveyItem surveyItem : survey.getSurveyItems()) {
            surveyItem.setSurveyId(savedSurveyEntity.getId());
            SurveyItem savedSurveyItem = surveyItemRepository.save(surveyItem);
            surveyItems.add(savedSurveyItem);
        }
        return savedSurveyEntity.toDomain(surveyItems);
    }

    @Override
    public Survey findById(Long surveyId) {
        List<SurveyItem> surveyItems = surveyItemRepository.findLatestSurveyItemsBySurveyId(surveyId);
        return surveyJpaRepository.findById(surveyId)
            .orElseThrow(() -> new CustomException(CustomResponseStatus.NOT_FOUND_SURVEY))
            .toDomain(surveyItems);
    }

}
