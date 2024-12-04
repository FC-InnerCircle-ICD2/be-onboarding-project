package com.innercircle.surveryproject.modules.repository;

import com.innercircle.surveryproject.modules.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findByName(String name);

}
