package com.onboarding.survey.service;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.enums.QuestionType;
import com.onboarding.survey.repository.QuestionRepository;
import java.util.List;
import java.util.stream.Collectors;
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

  public Question validateAndCreateQuestion(Question question) {
    // SINGLE_CHOICE 또는 MULTIPLE_CHOICE 타입일 경우 중복 제거
    if (question.getType() == QuestionType.SINGLE_CHOICE || question.getType() == QuestionType.MULTIPLE_CHOICE) {
      List<String> uniqueChoices = question.getChoices()
          .stream()
          .distinct()
          .collect(Collectors.toList());
      question.setChoices(uniqueChoices);

      if (uniqueChoices.isEmpty()) {
        throw new CustomException("Choices cannot be empty after removing duplicates", ErrorCode.INTERNAL_SERVER_ERROR);
      }
    }


    // 다른 타입은 그대로 반환
    return question;
  }
}
