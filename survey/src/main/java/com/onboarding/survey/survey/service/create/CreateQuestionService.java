package com.onboarding.survey.survey.service.create;

import com.onboarding.survey.survey.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateQuestionService {
  private final QuestionRepository questionRepository;

  public void createQuestion() {
    log.info("Creating question");
  }

}
