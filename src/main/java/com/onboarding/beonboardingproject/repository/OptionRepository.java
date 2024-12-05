package com.onboarding.beonboardingproject.repository;

import com.onboarding.beonboardingproject.domain.survey.Option;
import com.onboarding.beonboardingproject.domain.survey.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    void deleteByQuestion(Question question);

    List<Option> findByQuestion(Question question);
}
