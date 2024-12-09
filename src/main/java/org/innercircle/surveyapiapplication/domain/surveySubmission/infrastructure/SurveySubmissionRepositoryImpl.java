package org.innercircle.surveyapiapplication.domain.surveySubmission.infrastructure;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.entity.SurveySubmissionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SurveySubmissionRepositoryImpl implements SurveySubmissionRepository {

    private final SurveySubmissionJpaRepository surveySubmissionJpaRepository;

    @Override
    public SurveySubmission<?> save(SurveySubmission<?> surveySubmission) {
        return surveySubmissionJpaRepository.save(SurveySubmissionEntity.from(surveySubmission)).toDomain();
    }

    @Override
    public List<SurveySubmission<?>> findBySurveyItemIdAndVersion(Long surveyItemId, int surveyItemVersion) {
        return surveySubmissionJpaRepository.findBySurveyItemIdAndSurveyItemVersion(surveyItemId, surveyItemVersion)
            .stream().map(SurveySubmissionEntity::toDomain).collect(Collectors.toList());
    }

}
