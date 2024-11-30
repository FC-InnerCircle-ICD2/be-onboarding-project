package com.onboarding.beonboardingproject.repository;

import com.onboarding.beonboardingproject.domain.survey.Option;
import com.onboarding.beonboardingproject.domain.survey.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    void deleteByQuestion(Question question);
}
