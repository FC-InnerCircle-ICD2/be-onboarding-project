package com.onboarding.survey.facade;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.enums.QuestionType;
import com.onboarding.survey.object.QuestionObject;
import com.onboarding.survey.object.SurveyObject;
import com.onboarding.survey.dto.response.QuestionDTO;
import com.onboarding.survey.dto.response.SurveyWithQuestionsDTO;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.service.QuestionService;
import com.onboarding.survey.service.SurveyService;
import com.onboarding.survey.util.QuestionValidator;
import com.onboarding.survey.util.SurveyUpdateHandler;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class SurveyFacade {
  private final SurveyService surveyService;
  private final QuestionService questionService;
  private final QuestionValidator questionValidator;
  private final EntityManager entityManager;

  public void createSurvey(SurveyObject surveyObject) {
    Survey survey = surveyObject.of(
        surveyObject.getQuestions().stream().map(questionDto -> {
          log.info("questionDto isRequired: {}", questionDto.isRequired());
          log.info("questionDto isDeleted: {}", questionDto.isDeleted());

          // 검증 후 생성된 Question 반환
          Question question = questionService.validateAndCreateQuestion(questionDto.of(null));
          if (question == null) {
            throw new CustomException("Failed to create question", ErrorCode.INVALID_INPUT_VALUE);
          }
          return question;
        }).toList()
    );

    survey.getQuestions().forEach(question -> question.setSurvey(survey));
    surveyService.createSurvey(survey);
  }

  public void updateSurvey(Long surveyId, SurveyObject surveyObject) {
    Survey existingSurvey = surveyService.getSurveyById(surveyId);

    // Survey 이름 및 설명 업데이트
    existingSurvey.updateName(surveyObject.getSurveyName());
    existingSurvey.updateDescription(surveyObject.getSurveyDescription());

    // 질문 업데이트
    questionService.updateQuestions(existingSurvey, surveyObject.getQuestions());

    entityManager.flush(); // 변경 사항 강제 반영

    validateSurvey(existingSurvey);
  }



  // 트랜잭션 종료 후 검증 실행
  public void validateAfterTransaction(Survey survey) {
    survey.getQuestions().forEach(question -> questionValidator.validate(question));
  }

  public void validateSurvey(Survey survey) {
    for (Question question : survey.getQuestions()) {
      // choices 강제 초기화
      Hibernate.initialize(question.getChoices());
      log.info("Validating question: {}, choices: {}", question.getTitle(), question.getChoices());
      questionValidator.validate(question);
    }
  }

  private void handleTypeChange(Question existingQuestion, QuestionObject questionObject) {
    if (isTypeChangedToShortOrLongAnswer(existingQuestion, questionObject)) {
      // SHORT_ANSWER 또는 LONG_ANSWER로 변경 시 choices 초기화
      existingQuestion.setChoices(null);
    }
  }

  private boolean isTypeChangedToShortOrLongAnswer(Question existingQuestion, QuestionObject questionObject) {
    return (questionObject.getType() == QuestionType.SHORT_ANSWER || questionObject.getType() == QuestionType.LONG_ANSWER) &&
        existingQuestion.getType() != questionObject.getType();
  }

  private List<String> sanitizeChoices(QuestionObject questionObject) {
    // SINGLE_CHOICE와 MULTIPLE_CHOICE만 choices를 사용
    if (questionObject.getType() == QuestionType.SINGLE_CHOICE || questionObject.getType() == QuestionType.MULTIPLE_CHOICE) {
      return (questionObject.getChoices() == null || questionObject.getChoices().isEmpty())
          ? null
          : questionObject.getChoices();
    }
    return null;
  }



//  // 설문 저장
//  public Survey swapQuestionOrder(Long surveyId, Long questionId, Long targetQuestionId) {
//    Survey survey = surveyService.getSurveyById(surveyId);
//
//    Question question = survey.getQuestions().stream()
//        .filter(q -> q.getId().equals(questionId))
//        .findFirst()
//        .orElseThrow(() -> new CustomException(
//            "Required question is missing: " , ErrorCode.QUESTION_NOT_FOUND));
//
//    Question targetQuestion = survey.getQuestions().stream()
//        .filter(q -> q.getId().equals(targetQuestionId))
//        .findFirst()
//        .orElseThrow(() -> new CustomException(
//            "Required question is missing: ", ErrorCode.QUESTION_NOT_FOUND));
//
//    int tempOrderIndex = question.getOrderIndex();
//    question.setOrderIndex(targetQuestion.getOrderIndex());
//    targetQuestion.setOrderIndex(tempOrderIndex);
//
//    survey.getQuestions().sort(Comparator.comparingInt(Question::getOrderIndex)); // 정렬
//
//    return survey;
//  }

  public Survey findById(Long id) {
    return surveyService.getSurveyById(id);
  }

  @Transactional(readOnly = true)
  public SurveyWithQuestionsDTO getSurveyByIdWithQuestions(Long surveyId) {
    Survey survey = surveyService.findSurveyById(surveyId)
        .orElseThrow(() -> new CustomException("Survey not found", ErrorCode.SURVEY_NOT_FOUND));

    return new SurveyWithQuestionsDTO(
        survey.getId(),
        survey.getName(),
        survey.getDescription(),
        survey.getQuestions().stream()
            .map(question -> new QuestionDTO(
                question.getId(),
                question.getTitle(),
                question.getDescription(),
                question.getType().name(),
                question.isRequired(),
                question.getChoices()
            ))
            .toList()
    );
  }
}
