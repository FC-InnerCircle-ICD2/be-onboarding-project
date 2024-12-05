package ic2.onboarding.survey.repository;

import ic2.onboarding.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SurveyJpaRepository extends JpaRepository<Survey, String> {

    Optional<Survey> findFirstByUuid(String uuid);

}
