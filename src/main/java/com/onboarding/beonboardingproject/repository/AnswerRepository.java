package com.onboarding.beonboardingproject.repository;

import com.onboarding.beonboardingproject.domain.response.Answer;
import com.onboarding.beonboardingproject.domain.survey.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestion(Question question);
}
