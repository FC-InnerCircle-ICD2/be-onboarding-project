package com.onboarding.survey.repository;

import com.onboarding.survey.entity.Survey;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
  @EntityGraph(attributePaths = {"questions"})
  Optional<Survey> findById(Long id);
}
