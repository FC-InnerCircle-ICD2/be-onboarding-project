package com.onboarding.survey.service.read;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadQuestionService {

  private final QuestionRepository questionRepository;

  public Question getQuestionById(Long questionId) {
    return questionRepository.findById(questionId).orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
  }
}
