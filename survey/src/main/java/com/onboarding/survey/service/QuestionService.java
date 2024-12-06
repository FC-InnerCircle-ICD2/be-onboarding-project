package com.onboarding.survey.service;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.QuestionType;
import com.onboarding.survey.object.QuestionObject;
import com.onboarding.survey.repository.QuestionRepository;
import com.onboarding.survey.util.QuestionValidator;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final EntityManager entityManager;

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

  public void updateQuestions(Survey survey, List<QuestionObject> questionObjects) {
    for (QuestionObject questionObject : questionObjects) {
      survey.getQuestions().stream()
          .filter(q -> q.getTitle().equals(questionObject.getTitle()))
          .findFirst()
          .ifPresentOrElse(
              existingQuestion -> {
                // 기존 질문의 필드 업데이트 (ID 유지)
                existingQuestion.updateDetails(
                    questionObject.getTitle(),
                    questionObject.getDescription(),
                    questionObject.getType(),
                    questionObject.isRequired(),
                    questionObject.getChoices()
                );
                log.info("Updated existing question: {}", existingQuestion);
              },
              () -> {
                // 새로운 질문 추가
                Question newQuestion = Question.builder()
                    .title(questionObject.getTitle())
                    .description(questionObject.getDescription())
                    .type(questionObject.getType())
                    .isRequired(questionObject.isRequired())
                    .choices(questionObject.getChoices())
                    .survey(survey)
                    .build();

                survey.addQuestion(newQuestion);
                log.info("Added new question: {}", newQuestion);
              }
          );
    }
  }

  private boolean isTypeChangedToShortOrLongAnswer(Question existingQuestion, QuestionObject questionObject) {
    return (questionObject.getType() == QuestionType.SHORT_ANSWER || questionObject.getType() == QuestionType.LONG_ANSWER) &&
        existingQuestion.getType() != questionObject.getType();
  }
}
