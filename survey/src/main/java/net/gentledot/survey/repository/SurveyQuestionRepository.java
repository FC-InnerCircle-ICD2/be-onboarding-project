package net.gentledot.survey.repository;

import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
}
