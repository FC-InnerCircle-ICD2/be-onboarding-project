package org.innercircle.surveyapiapplication.domain.question.infrastructure;

import org.innercircle.surveyapiapplication.domain.question.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, Long> {

    List<QuestionEntity> findBySurveyId(Long surveyId);

}
