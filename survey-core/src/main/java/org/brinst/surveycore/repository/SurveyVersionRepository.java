package org.brinst.surveycore.repository;

import org.brinst.surveycore.entity.Survey;
import org.brinst.surveycore.entity.SurveyVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyVersionRepository extends JpaRepository<SurveyVersion, Long> {
	SurveyVersion findByVersionAndSurvey(int version, Survey survey);
}
