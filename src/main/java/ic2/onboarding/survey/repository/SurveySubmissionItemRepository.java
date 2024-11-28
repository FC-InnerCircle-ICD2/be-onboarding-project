package ic2.onboarding.survey.repository;

import ic2.onboarding.survey.entity.SurveySubmissionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveySubmissionItemRepository extends JpaRepository<SurveySubmissionItem, Long> {
}
