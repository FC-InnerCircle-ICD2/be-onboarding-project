package com.onboarding.survey.survey.repository;

import com.onboarding.survey.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
