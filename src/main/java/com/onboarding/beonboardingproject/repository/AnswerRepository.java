package com.onboarding.beonboardingproject.repository;

import com.onboarding.beonboardingproject.domain.response.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
