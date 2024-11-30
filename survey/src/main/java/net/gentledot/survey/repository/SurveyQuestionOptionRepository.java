package net.gentledot.survey.repository;

import net.gentledot.survey.model.entity.SurveyQuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyQuestionOptionRepository extends JpaRepository<SurveyQuestionOption, Long> {
}
