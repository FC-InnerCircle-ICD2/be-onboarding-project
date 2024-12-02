package org.brinst.surveycore.survey.repository;

import org.brinst.surveycore.survey.entity.Survey;
import org.brinst.surveycore.survey.entity.SurveyVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyVersionRepository extends JpaRepository<SurveyVersion, Long> {
	SurveyVersion findByVersionAndSurvey(int version, Survey survey);
}
