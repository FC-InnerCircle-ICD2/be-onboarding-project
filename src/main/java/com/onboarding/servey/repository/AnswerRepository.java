package com.onboarding.servey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onboarding.servey.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
