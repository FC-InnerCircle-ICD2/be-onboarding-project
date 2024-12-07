package net.gentledot.survey.infra.repository.jpa;

import net.gentledot.survey.domain.surveybase.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
}
