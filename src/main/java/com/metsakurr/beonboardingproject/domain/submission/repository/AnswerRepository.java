package com.metsakurr.beonboardingproject.domain.submission.repository;

import com.metsakurr.beonboardingproject.domain.submission.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
