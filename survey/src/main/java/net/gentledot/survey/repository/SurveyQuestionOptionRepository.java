package net.gentledot.survey.repository;

import net.gentledot.survey.model.entity.surveybase.SurveyQuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionOptionRepository extends JpaRepository<SurveyQuestionOption, Long> {
}
