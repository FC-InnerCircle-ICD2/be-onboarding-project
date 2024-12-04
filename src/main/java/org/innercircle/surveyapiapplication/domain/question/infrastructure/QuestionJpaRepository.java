package org.innercircle.surveyapiapplication.domain.question.infrastructure;

import org.innercircle.surveyapiapplication.domain.question.entity.QuestionEntity;
import org.innercircle.surveyapiapplication.domain.question.entity.id.QuestionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, QuestionId> {

    List<QuestionEntity> findBySurveyId(Long surveyId);

}
