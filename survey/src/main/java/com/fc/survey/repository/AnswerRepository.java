package com.fc.survey.repository;

import com.fc.survey.entity.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

  List<Answer> findAllBySurveyId(Long id);
}
