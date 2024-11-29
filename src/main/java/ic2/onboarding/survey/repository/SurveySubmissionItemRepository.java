package ic2.onboarding.survey.repository;

import ic2.onboarding.survey.entity.SurveySubmissionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveySubmissionItemRepository extends JpaRepository<SurveySubmissionItem, Long> {

    List<SurveySubmissionItem> findAllBySurveyIdOrderByIdAsc(Long id);

}
