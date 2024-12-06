package org.innercircle.surveyapiapplication.domain.surveySubmission.infrastructure;

import org.innercircle.surveyapiapplication.domain.surveySubmission.entity.SurveySubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveySubmissionJpaRepository extends JpaRepository<SurveySubmissionEntity, Long> {

    List<SurveySubmissionEntity> findBySurveyItemIdAndSurveyItemVersion(Long surveyItemId, int surveyItemVersion);

}
