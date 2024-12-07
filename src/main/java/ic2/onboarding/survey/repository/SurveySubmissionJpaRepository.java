package ic2.onboarding.survey.repository;

import ic2.onboarding.survey.entity.SurveySubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface SurveySubmissionJpaRepository extends JpaRepository<SurveySubmission, Long> {

    List<SurveySubmission> findAllBySurveyUuid(String surveyUuid);

}
