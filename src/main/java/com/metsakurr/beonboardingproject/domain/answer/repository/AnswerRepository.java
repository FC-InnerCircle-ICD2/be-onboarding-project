package com.metsakurr.beonboardingproject.domain.answer.repository;

import com.metsakurr.beonboardingproject.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
