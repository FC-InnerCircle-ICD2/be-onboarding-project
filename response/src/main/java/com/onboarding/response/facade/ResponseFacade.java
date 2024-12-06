package com.onboarding.response.facade;

import com.onboarding.core.global.exception.CustomException;
import com.onboarding.core.global.exception.enums.ErrorCode;
import com.onboarding.core.global.utils.RedisLock;
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
  private final RedisLock redisLock;

  @Transactional
  public void submitResponse(Long surveyId, ResponseObject responseObject) {
    String lockKey = "survey:lock:" + surveyId;
    if (!redisLock.lock(lockKey, 10)) {
      log.error("Failed to acquire lock for surveyId: {}", surveyId);
      throw new CustomException("Failed to acquire lock", ErrorCode.CONCURRENT_MODIFICATION);
    }

    try {
      log.info("Acquired lock for surveyId: {}", surveyId);

      // 설문 존재 여부 확인
      Survey survey = surveyService.findSurveyById(surveyId)
          .orElseThrow(() -> new CustomException("Survey not found", ErrorCode.SURVEY_NOT_FOUND));
      log.info("Survey found: {}", survey.getName());

      // 사용자 중복 응답 확인
      if (responseService.existsBySurveyIdAndEmail(surveyId, responseObject.email())) {
        throw new CustomException("User has already submitted the survey", ErrorCode.ENTITY_ALREADY_EXISTS);
      }

      List<Question> surveyQuestions = questionService.findQuestionsBySurveyId(surveyId);
      log.info("Questions loaded: {}", surveyQuestions.size());

      validateRequiredQuestions(surveyQuestions, responseObject.answerObjects());
      log.info("Required questions validated");

      List<Answer> answers = responseObject.answerObjects().stream()
          .map(request -> {
            // 중복 응답 확인
            if (responseService.hasUserAlreadyAnsweredQuestion(surveyId, request.questionTitle(), responseObject.email())) {
              throw new CustomException("User has already answered this question: " + request.questionTitle(), ErrorCode.ENTITY_ALREADY_EXISTS);
            }

            validateAnswerObject(request, surveyId);
            Question question = findMatchingQuestion(surveyQuestions, request.questionTitle());

            if (question == null) {
              log.error("Invalid question: {}", request.questionTitle());
              throw new CustomException("Invalid question", ErrorCode.QUESTION_NOT_FOUND);
            }
            return createAnswerFromRequest(question, request);
          }).toList();

      log.info("Answers created: {}", answers.size());
      responseService.saveResponse(survey, responseObject.email(), answers);
      log.info("Response saved successfully");
    } finally {
      redisLock.unlock(lockKey);
      log.info("Lock released for surveyId: {}", surveyId);
    }
  }




  private void validateAnswerObject(AnswerObject answer, Long surveyId) {
    // Question을 조회하여 선택지를 검증
    Question question = findMatchingQuestionForAnswer(answer, surveyId);
    if (question == null) {
      throw new CustomException("Question not found: " + answer.questionTitle(), ErrorCode.QUESTION_NOT_FOUND);
    }

    // Single Choice 또는 Multiple Choice의 선택지 검증
    if (question.getType() == QuestionType.SINGLE_CHOICE || question.getType() == QuestionType.MULTIPLE_CHOICE) {
      List<String> validChoices = question.getChoices(); // 설문에 등록된 선택지
      List<String> submittedChoices = answer.choices(); // 응답자가 제출한 선택지

      // Single Choice: 하나의 값만 제출되며, 그것이 유효한지 확인
      if (question.getType() == QuestionType.SINGLE_CHOICE) {
        if (submittedChoices.size() != 1 || !validChoices.contains(submittedChoices.get(0))) {
          throw new CustomException("Invalid choice for question: " + question.getTitle(), ErrorCode.INVALID_INPUT_VALUE);
        }
      }

      // Multiple Choice: 모든 제출된 값이 유효한지 확인
      if (question.getType() == QuestionType.MULTIPLE_CHOICE) {
        List<String> invalidChoices = submittedChoices.stream()
            .filter(choice -> !validChoices.contains(choice))
            .toList();
        if (!invalidChoices.isEmpty()) {
          throw new CustomException("Invalid choices: " + invalidChoices + " for question: " + question.getTitle(), ErrorCode.INVALID_INPUT_VALUE);
        }
      }
    }

    // SHORT_ANSWER, LONG_ANSWER에 대한 추가 검증 로직 필요 시 여기에 추가
  }


  private Question findMatchingQuestionForAnswer(AnswerObject answer, Long surveyId) {
    return questionService.findQuestionsBySurveyId(surveyId).stream()
        .filter(question -> question.getTitle().equals(answer.questionTitle()))
        .findFirst()
        .orElse(null);
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
