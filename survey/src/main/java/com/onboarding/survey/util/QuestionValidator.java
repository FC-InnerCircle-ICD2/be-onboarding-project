package com.onboarding.survey.util;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.enums.QuestionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuestionValidator {

  public void validate(Question question) {
    log.info("Validating question: {}, type: {}, choices: {}", question.getTitle(), question.getType(), question.getChoices());

    if (question.getType() == QuestionType.SINGLE_CHOICE || question.getType() == QuestionType.MULTIPLE_CHOICE) {
      if (question.getChoices() == null || question.getChoices().isEmpty()) {
        throw new CustomException("Choices must not be empty for SINGLE_CHOICE or MULTIPLE_CHOICE", ErrorCode.INVALID_INPUT_VALUE);
      }

      if (question.getType() == QuestionType.MULTIPLE_CHOICE && question.getChoices().size() < 2) {
        throw new CustomException("Choices must have at least 2 options for MULTIPLE_CHOICE questions", ErrorCode.INVALID_INPUT_VALUE);
      }
    }

    if ((question.getType() == QuestionType.SHORT_ANSWER || question.getType() == QuestionType.LONG_ANSWER) &&
        (question.getChoices() != null && !question.getChoices().isEmpty())) {
      throw new CustomException("Choices must be empty for SHORT_ANSWER or LONG_ANSWER", ErrorCode.INVALID_INPUT_VALUE);
    }
  }

}
