package com.onboarding.beonboardingproject.service;

import com.onboarding.beonboardingproject.domain.response.Answer;
import com.onboarding.beonboardingproject.domain.response.Response;
import com.onboarding.beonboardingproject.domain.survey.Option;
import com.onboarding.beonboardingproject.domain.survey.Question;
import com.onboarding.beonboardingproject.domain.survey.QuestionType;
import com.onboarding.beonboardingproject.domain.survey.Survey;
import com.onboarding.beonboardingproject.dto.*;
import com.onboarding.beonboardingproject.repository.OptionRepository;
import com.onboarding.beonboardingproject.repository.QuestionRepository;
import com.onboarding.beonboardingproject.repository.ResponseRepository;
import com.onboarding.beonboardingproject.repository.SurveyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    // 설문 조사 생성
    public SurveyResponseDto createSurvey(SurveyDto surveyDto) {
        Survey survey = surveyDto.toEntity();

        surveyRepository.save(survey);

        return new SurveyResponseDto(survey);
    }

    // 설문 조사 수정
    public void updateSurvey(Long surveyId, SurveyDto surveyDto) {
        // 1. 기존 설문조사 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 설문조사를 찾지 못했습니다. : " + surveyId));

        // 2. 설문조사 기본 정보 수정
        survey.setSurveyName(surveyDto.getSurveyName());
        survey.setSurveyDesc(surveyDto.getSurveyDesc());

        // 3. 질문 추가/수정/삭제 처리
        List<QuestionDto> updatedQuestions = surveyDto.getQuestions();

        for (QuestionDto questionDto : updatedQuestions) {
            if (questionDto.getQuestionId() == null) {
                // 새로운 질문 추가
                Question newQuestion = Question.builder()
                        .questionName(questionDto.getQuestionName())
                        .questionDesc(questionDto.getQuestionDesc())
                        .questionType(QuestionType.valueOf(questionDto.getQuestionType()))
                        .isRequired(questionDto.isRequired())
                        .survey(survey)
                        .build();

                survey.addQuestion(newQuestion);

                // 옵션 추가 (단일/다중 선택형인 경우)
                if (isChoiceType(questionDto.getQuestionType())) {
                    addOptionsToQuestion(newQuestion, questionDto.getOptions());
                }
            } else {
                // 기존 질문 수정
                Question existingQuestion = questionRepository.findById(questionDto.getQuestionId())
                        .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionDto.getQuestionId()));

                existingQuestion.setQuestionName(questionDto.getQuestionName());
                existingQuestion.setQuestionDesc(questionDto.getQuestionDesc());
                existingQuestion.setQuestionType(QuestionType.valueOf(questionDto.getQuestionType()));
                existingQuestion.setRequired(questionDto.isRequired());

                // 기존 옵션 삭제 및 새로운 옵션 추가
                if (isChoiceType(questionDto.getQuestionType())) {
                    optionRepository.deleteByQuestion(existingQuestion); // 기존 옵션 삭제
                    addOptionsToQuestion(existingQuestion, questionDto.getOptions());
                }
            }
        }
        surveyRepository.save(survey);
    }

    private boolean isChoiceType(String questionType) {
        return "SINGLE_CHOICE".equals(questionType) || "MULTIPLE_CHOICE".equals(questionType);
    }

    private void addOptionsToQuestion(Question question, List<OptionDto> options) {
        for (OptionDto optionDto : options) {
            Option option = Option.builder()
                    .optionValue(optionDto.getOptionValue())
                    .question(question)
                    .build();
            question.addOption(option);
        }
    }

    // 설문 응답 제출
    public void submitResponse(ResponseDto responseDto) {
        // 1. 설문조사 조회
        Survey survey = surveyRepository.findById(responseDto.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        // 2. 새로운 Response 엔티티 생성
        Response response = Response.builder()
                .survey(survey)
                .build();

        // 3. 응답에 포함된 각 질문과 답변을 처리
        for (AnswerDto answerDto : responseDto.getAnswers()) {
            // 질문 조회
            Question question = questionRepository.findById(answerDto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            // Answer 생성 및 설정
            Answer answer = Answer.builder()
                    .question(question)
                    .response(response)
                    .answerValue(answerDto.getAnswerValue())
                    .build();

            // Response에 Answer 추가
            response.addAnswer(answer);
        }

        // 4. 응답 저장
        responseRepository.save(response);
    }

    // 설문 응답 조회
    public List<ResponseDto> getResponses(Long surveyId) {
        // 1. 설문 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        // 2. 설문에 해당하는 모든 응답 조회
        return ResponseDto.listOf(responseRepository.findBySurvey(survey));
    }
}
