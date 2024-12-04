package com.onboarding.survey.facade;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.survey.dto.QuestionObject;
import com.onboarding.survey.dto.SurveyObject;
import com.onboarding.survey.dto.response.QuestionDTO;
import com.onboarding.survey.dto.response.SurveyWithQuestionsDTO;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.service.QuestionService;
import com.onboarding.survey.service.SurveyService;
import com.onboarding.survey.util.SurveyUpdateHandler;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class SurveyFacade {
  private final SurveyService surveyService;
  private final QuestionService questionService;
  private final SurveyUpdateHandler surveyUpdateHandler;

  public void createSurvey(SurveyObject surveyObject) {
    Survey survey = surveyObject.of(
        surveyObject.questions().stream().map(questionDto -> questionDto.of(null)).toList()
    );
    survey.getQuestions().forEach(question -> question.setSurvey(survey));
    surveyService.createSurvey(survey);
  }

  public void updateSurvey(Long surveyId, SurveyObject surveyObject) {
    Survey existingSurvey = surveyService.getSurveyById(surveyId);

    // 설문 이름과 설명 업데이트
    existingSurvey.updateName(surveyObject.surveyName());
    existingSurvey.updateDescription(surveyObject.surveyDescription());

    List<Question> existingQuestions = new ArrayList<>(existingSurvey.getQuestions());
    List<QuestionObject> newQuestions = surveyObject.questions();

    // 기존 질문 중 새 질문 리스트에 없는 항목 삭제
    for (Question existing : new ArrayList<>(existingQuestions)) {
      boolean isPresentInNew = newQuestions.stream()
          .anyMatch(newQ -> newQ.getTitle().equals(existing.getTitle()));
      if (!isPresentInNew) {
        existingSurvey.removeQuestion(existing);
      }
    }

    // 새 질문 추가 또는 업데이트
    for (QuestionObject questionObject : newQuestions) {
      Question matchingQuestion = existingSurvey.getQuestions().stream()
          .filter(existing -> existing.getTitle().equals(questionObject.getTitle()))
          .findFirst()
          .orElse(null);

      if (matchingQuestion != null) {
        // 기존 질문 업데이트
        matchingQuestion.updateDetails(
            questionObject.getTitle(),
            questionObject.getDescription(),
            questionObject.getType(),
            questionObject.isRequired(),
            questionObject.getChoices()
        );
      } else {
        // 새 질문 추가
        Question newQuestion = Question.builder()
            .title(questionObject.getTitle())
            .description(questionObject.getDescription())
            .type(questionObject.getType())
            .isRequired(questionObject.isRequired())
            .choices(questionObject.getChoices())
            .build();
        existingSurvey.addQuestion(newQuestion);
      }
    }

    // 디버깅용 로그
    System.out.println("Final Questions in Survey: " + existingSurvey.getQuestions().size());

    surveyService.createSurvey(existingSurvey);

  }

  // 설문 저장
  public Survey swapQuestionOrder(Long surveyId, Long questionId, Long targetQuestionId) {
    Survey survey = surveyService.getSurveyById(surveyId);

    Question question = survey.getQuestions().stream()
        .filter(q -> q.getId().equals(questionId))
        .findFirst()
        .orElseThrow(() -> new CustomException(
            "Required question is missing: " , ErrorCode.QUESTION_NOT_FOUND));

    Question targetQuestion = survey.getQuestions().stream()
        .filter(q -> q.getId().equals(targetQuestionId))
        .findFirst()
        .orElseThrow(() -> new CustomException(
            "Required question is missing: ", ErrorCode.QUESTION_NOT_FOUND));

    int tempOrderIndex = question.getOrderIndex();
    question.setOrderIndex(targetQuestion.getOrderIndex());
    targetQuestion.setOrderIndex(tempOrderIndex);

    survey.getQuestions().sort(Comparator.comparingInt(Question::getOrderIndex)); // 정렬

    return survey;
  }

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
