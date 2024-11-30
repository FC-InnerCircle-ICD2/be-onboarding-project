package com.onboarding.survey.facade;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.dto.QuestionObject;
import com.onboarding.survey.dto.SurveyObject;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.service.create.CreateSurveyService;
import com.onboarding.survey.service.read.ReadQuestionService;
import com.onboarding.survey.service.read.ReadSurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class SurveyFacade {
  private final CreateSurveyService createSurveyService;
  private final ReadSurveyService readSurveyService;
  private final ReadQuestionService readQuestionService;

  public void createSurvey(SurveyObject surveyObject) {
    Survey survey = surveyObject.of(
        surveyObject.questions().stream().map(questionDto -> questionDto.of(null)).toList()
    );
    survey.getQuestions().forEach(question -> question.setSurvey(survey));
    createSurveyService.createSurvey(survey);
  }

  public void updateSurvey(Long surveyId, SurveyObject surveyObject) {
    Survey survey = readSurveyService.getSurveyById(surveyId);

    survey.updateName(surveyObject.surveyName());
    survey.updateDescription(surveyObject.surveyDescription());

    if (surveyObject.questions() != null) {
      for (QuestionObject questionObject : surveyObject.questions()) {
        if (questionObject.operation() == null) {
          throw new CustomException(ErrorCode.INVALID__REQUEST);
        }

        switch (questionObject.operation()) {
          case ADD -> {
            Question question = questionObject.of(survey);
            survey.addQuestion(question);
          }

          case UPDATE -> {
            if (questionObject.id() == null) {
              throw new CustomException(ErrorCode.INVALID__REQUEST);
            }
            Question existingQuestion = readQuestionService.getQuestionById(questionObject.id());

            if (existingQuestion == null) {
              throw new CustomException(ErrorCode.INVALID__REQUEST);
            }

            existingQuestion.updateDetails(
                questionObject.title(),
                questionObject.description(),
                questionObject.type(),
                questionObject.isRequired(),
                questionObject.choices()
            );
          }

          case DELETE -> {
            if (questionObject.id() == null) {
              throw new CustomException(ErrorCode.INVALID__REQUEST);
            }
            Question questionToDelete = readQuestionService.getQuestionById(questionObject.id());

            if (questionToDelete == null) {
              throw new CustomException(ErrorCode.INVALID__REQUEST);
            }

            survey.removeQuestion(questionToDelete);
          }

          default -> throw new CustomException(ErrorCode.INVALID__REQUEST);
        }
      }
    }

    createSurveyService.createSurvey(survey);
  }

  public Survey swapQuestionOrder(Long surveyId, Long questionId, Long targetQuestionId) {
    // 설문조사 및 질문 조회
    Survey survey = readSurveyService.getSurveyById(surveyId);

    Question question = survey.getQuestions().stream()
        .filter(q -> q.getId().equals(questionId))
        .findFirst()
        .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

    Question targetQuestion = survey.getQuestions().stream()
        .filter(q -> q.getId().equals(targetQuestionId))
        .findFirst()
        .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));

    // 두 질문의 orderIndex 교환
    int tempOrderIndex = question.getOrderIndex();
    question.setOrderIndex(targetQuestion.getOrderIndex());
    targetQuestion.setOrderIndex(tempOrderIndex);

    return survey;
  }
}
