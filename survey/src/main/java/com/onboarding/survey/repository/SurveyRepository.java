package com.onboarding.survey.repository;

import com.onboarding.survey.entity.Survey;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
  @EntityGraph(attributePaths = {"questions"})
  Optional<Survey> findById(Long id);

  @EntityGraph(attributePaths = {"questions"})
  @Query("SELECT s FROM Survey s JOIN FETCH s.questions q WHERE s.id = :id AND q.isDeleted = false")
  Optional<Survey> findByIdWithActiveQuestions(Long id);
}
