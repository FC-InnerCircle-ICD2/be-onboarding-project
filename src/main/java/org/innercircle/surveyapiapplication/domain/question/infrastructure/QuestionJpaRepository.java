package org.innercircle.surveyapiapplication.domain.question.infrastructure;

import org.innercircle.surveyapiapplication.domain.question.entity.QuestionEntity;
import org.innercircle.surveyapiapplication.domain.question.entity.id.QuestionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionJpaRepository extends JpaRepository<QuestionEntity, QuestionId> {

    List<QuestionEntity> findBySurveyId(Long surveyId);

    @Query("SELECT q FROM QuestionEntity q WHERE q.questionId.id = :id AND q.questionId.version = (SELECT MAX(q2.questionId.version) FROM QuestionEntity q2 WHERE q2.questionId.id = :id)")
    Optional<QuestionEntity> findLatestQuestionById(@Param("id") Long id);

}
