package net.gentledot.survey.repository;

import net.gentledot.survey.model.entity.surveybase.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, String> {
}
