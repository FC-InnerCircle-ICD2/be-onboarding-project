package com.onboarding.response.facade;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.response.dto.response.AnswerDTO;
import com.onboarding.response.dto.response.ResponseDTO;
import com.onboarding.response.entity.Answer;
import com.onboarding.response.entity.QuestionSnapshot;
import com.onboarding.response.entity.Response;
import com.onboarding.response.entity.ResponseValue;
import com.onboarding.response.object.AnswerObject;
import com.onboarding.response.object.ResponseObject;
import com.onboarding.response.service.ResponseService;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import com.onboarding.survey.enums.QuestionType;
import com.onboarding.survey.service.QuestionService;
import com.onboarding.survey.service.SurveyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResponseFacade {
  private final SurveyService surveyService;
  private final QuestionService questionService;
  private final ResponseService responseService;

  @Transactional
  public void submitResponse(Long surveyId, ResponseObject responseObject) {
    Survey survey = surveyService.findSurveyById(surveyId)
        .orElseThrow(() -> new CustomException("Survey not found", ErrorCode.SURVEY_NOT_FOUND));

    List<Question> surveyQuestions = questionService.findQuestionsBySurveyId(surveyId);

    validateRequiredQuestions(surveyQuestions, responseObject.answerObjects());
    responseObject.answerObjects().forEach(this::validateAnswerObject);

    List<Answer> answers = responseObject.answerObjects().stream()
        .map(request -> {
          Question question = findMatchingQuestion(surveyQuestions, request.questionTitle());
          if (question == null) {
            throw new CustomException("Invalid question: " + request.questionTitle(), ErrorCode.QUESTION_NOT_FOUND);
          }
          return createAnswerFromRequest(question, request);
        }).toList();

    responseService.saveResponse(survey, responseObject.email(), answers);
  }

  private void validateAnswerObject(AnswerObject answer) {
    if ((answer.questionType().equals("SINGLE_CHOICE") || answer.questionType().equals("MULTIPLE_CHOICE")) &&
        (answer.choices() == null || answer.choices().isEmpty())) {
      throw new CustomException("Choices must not be empty for SINGLE_CHOICE or MULTIPLE_CHOICE", ErrorCode.INVALID_INPUT_VALUE);
    }

    if ((answer.questionType().equals("SHORT_ANSWER") || answer.questionType().equals("LONG_ANSWER")) &&
        (answer.choices() != null && !answer.choices().isEmpty())) {
      throw new CustomException("Choices must be empty for SHORT_ANSWER or LONG_ANSWER", ErrorCode.INVALID_INPUT_VALUE);
    }
  }

  public List<ResponseDTO> getAllResponses(Long surveyId) {
    // 1. 설문조사 존재 여부 확인
    Survey survey = surveyService.findSurveyById(surveyId)
        .orElseThrow(() -> new CustomException("Survey not found", ErrorCode.SURVEY_NOT_FOUND));

    // 2. 설문 응답 조회
    List<Response> responses = responseService.findResponsesBySurvey(survey);

    // 3. DTO 변환 및 반환
    return responses.stream()
        .map(this::convertToDTO)
        .toList();
  }

  public List<ResponseDTO> searchResponses(Long surveyId, String questionTitle, String responseValue) {
    // 1. 설문조사 존재 여부 확인
    Survey survey = surveyService.findSurveyById(surveyId)
        .orElseThrow(() -> new CustomException("Survey not found", ErrorCode.SURVEY_NOT_FOUND));

    // 2. 조건에 맞는 응답 조회
    List<Response> responses = responseService.searchResponses(survey, questionTitle, responseValue);

    // 3. DTO 변환 및 반환
    return responses.stream()
        .map(this::convertToDTO)
        .toList();
  }

  private ResponseDTO convertToDTO(Response response) {
    return new ResponseDTO(
        response.getEmail(),
        response.getAnswers().stream()
            .map(answer -> new AnswerDTO(
                answer.getQuestionSnapshot().getTitle(),
                answer.getResponseValue()))
            .toList()
    );
  }


  private Answer createAnswerFromRequest(Question question, AnswerObject request) {
    QuestionSnapshot snapshot = new QuestionSnapshot(
        question.getTitle(),
        question.getDescription(),
        question.getType(),
        question.getChoices(),
        question.isRequired()
    );

    ResponseValue responseValue = question.getType() == QuestionType.SHORT_ANSWER || question.getType() == QuestionType.LONG_ANSWER
        ? ResponseValue.forText(request.answer())
        : ResponseValue.forChoices(request.choices());

    Answer answer = Answer.builder()
        .questionSnapshot(snapshot)
        .responseValue(responseValue)
        .build();

    answer.validate();
    return answer;
  }

  private void validateRequiredQuestions(List<Question> surveyQuestions, List<AnswerObject> answerRequests) {
    List<String> providedTitles = answerRequests.stream()
        .map(AnswerObject::questionTitle)
        .toList();

    surveyQuestions.stream()
        .filter(Question::isRequired)
        .forEach(requiredQuestion -> {
          if (!providedTitles.contains(requiredQuestion.getTitle())) {
            throw new CustomException("Required question is missing: " + requiredQuestion.getTitle(), ErrorCode.MISSING_REQUIRED_ANSWER);
          }
        });
  }

  private Question findMatchingQuestion(List<Question> questions, String title) {
    return questions.stream()
        .filter(question -> question.getTitle().equals(title))
        .findFirst()
        .orElse(null);
  }

}
