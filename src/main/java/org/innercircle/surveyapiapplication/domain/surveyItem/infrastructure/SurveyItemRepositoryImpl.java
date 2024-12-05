package org.innercircle.surveyapiapplication.domain.surveyItem.infrastructure;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
import org.innercircle.surveyapiapplication.domain.surveyItem.entity.SurveyItemEntity;
import org.innercircle.surveyapiapplication.domain.surveyItem.entity.id.SurveyItemId;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SurveyItemRepositoryImpl implements SurveyItemRepository {

    private final SurveyItemJpaRepository surveyItemJpaRepository;

    @Override
    public SurveyItem save(SurveyItem surveyItem) {
        return surveyItemJpaRepository.save(SurveyItemEntity.from(surveyItem)).toDomain();
    }

    @Override
    public List<SurveyItem> findBySurveyId(Long surveyId) {
        return surveyItemJpaRepository.findBySurveyId(surveyId).stream().map(SurveyItemEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public SurveyItem findByIdAndVersion(Long id, int version) {
        SurveyItemId surveyItemId = new SurveyItemId(id, version);
        return surveyItemJpaRepository.findById(surveyItemId)
            .orElseThrow(() -> new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION))
            .toDomain();
    }

    @Override
    public SurveyItem findLatestQuestionById(Long questionId) {
        return surveyItemJpaRepository.findLatestQuestionById(questionId)
            .orElseThrow(() -> new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION))
            .toDomain();
    }

}
