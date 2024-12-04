package com.onboarding.survey.service;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.repository.QuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionRepository questionRepository;

  public Question getQuestionById(Long questionId) {
    return questionRepository.findById(questionId).orElseThrow(() -> new CustomException(
        "Required question is missing: ", ErrorCode.ENTITY_NOT_FOUND));
  }

  public List<Question> findQuestionsBySurveyId(Long surveyId) {
    return questionRepository.findBySurveyId(surveyId);
  }
}
