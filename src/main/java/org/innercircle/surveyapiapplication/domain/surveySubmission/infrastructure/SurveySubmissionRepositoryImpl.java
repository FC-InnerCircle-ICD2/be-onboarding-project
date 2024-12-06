package org.innercircle.surveyapiapplication.domain.surveySubmission.infrastructure;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.surveySubmission.domain.SurveySubmission;
import org.innercircle.surveyapiapplication.domain.surveySubmission.entity.SurveySubmissionEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SurveySubmissionRepositoryImpl implements SurveySubmissionRepository {

    private final SurveySubmissionJpaRepository surveySubmissionJpaRepository;

    @Override
    public SurveySubmission<?> save(SurveySubmission<?> surveySubmission) {
        return surveySubmissionJpaRepository.save(SurveySubmissionEntity.from(surveySubmission)).toDomain();
    }

}
