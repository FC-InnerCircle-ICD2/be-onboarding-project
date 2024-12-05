package com.onboarding.beonboardingproject.service;

import com.onboarding.beonboardingproject.common.exception.InvalidException;
import com.onboarding.beonboardingproject.domain.response.Answer;
import com.onboarding.beonboardingproject.domain.response.Response;
import com.onboarding.beonboardingproject.domain.survey.Option;
import com.onboarding.beonboardingproject.domain.survey.Question;
import com.onboarding.beonboardingproject.domain.survey.QuestionType;
import com.onboarding.beonboardingproject.domain.survey.Survey;
import com.onboarding.beonboardingproject.dto.*;
import com.onboarding.beonboardingproject.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final AnswerRepository answerRepository;

    // 설문 조사 생성
    public SurveyResponseDto createSurvey(SurveyDto surveyDto) {
        Survey survey = surveyDto.toEntity();

        surveyRepository.save(survey);

        return new SurveyResponseDto(survey);
    }

    // 설문 조사 수정
    public SurveyResponseDto updateSurvey(Long surveyId, SurveyDto surveyDto) {
        // 1. 기존 설문조사 조회
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new InvalidException("일치하는 설문조사를 찾지 못했습니다. [Survey ID : " + surveyId + "]"));

        List<Question> existingQuestions = new ArrayList<>(survey.getQuestions());
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
                Question existingQuestion = existingQuestions.stream()
                        .filter(q -> q.getId().equals(questionDto.getQuestionId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Question not found: " + questionDto.getQuestionId()));

                existingQuestion.setQuestionName(questionDto.getQuestionName());
                existingQuestion.setQuestionDesc(questionDto.getQuestionDesc());
                existingQuestion.setQuestionType(QuestionType.valueOf(questionDto.getQuestionType()));
                existingQuestion.setRequired(questionDto.isRequired());

                // 옵션 수정
                if(isChoiceType(questionDto.getQuestionType())) {
                    updateOptionsForQuestion(existingQuestion, questionDto.getOptions());
                }

                existingQuestions.remove(existingQuestion); // 수정된 질문은 삭제 목록에서 제거
            }
        }

        // 기존 질문 중 삭제해야 할 질문 처리
        for (Question questionToRemove : existingQuestions) {
            // 해당 Question에 대한 모든 Answer를 조회하고 상태 변경
            List<Answer> answers = answerRepository.findByQuestion(questionToRemove);
            for (Answer answer : answers) {
                answer.setDeletedQuestion(true); // 삭제된 질문으로 상태 변경
                answerRepository.save(answer); // Answer 저장
            }

            survey.removeQuestion(questionToRemove);
            questionRepository.delete(questionToRemove); // 데이터베이스에서 삭제
        }

        surveyRepository.save(survey);

        return new SurveyResponseDto(survey);
    }

    private void updateOptionsForQuestion(Question existingQuestion, List<OptionDto> updatedOptions) {
        // 기존 옵션 가져오기
        List<Option> existingOptions = optionRepository.findByQuestion(existingQuestion);

        // 기존 옵션을 Map으로 변환 (key: optionId)
        Map<Long, Option> existingOptionMap = existingOptions.stream()
                .collect(Collectors.toMap(Option::getId, option -> option));

        // 새로운 옵션 처리
        for (OptionDto optionDto : updatedOptions) {
            if (optionDto.getOptionId() == null) {
                // 새로운 옵션 추가
                Option newOption = Option.builder()
                        .optionValue(optionDto.getOptionValue())
                        .question(existingQuestion)
                        .build();
                optionRepository.save(newOption);
            } else if (existingOptionMap.containsKey(optionDto.getOptionId())) {
                // 기존 옵션 수정
                Option existingOption = existingOptionMap.get(optionDto.getOptionId());
                existingOption.setOptionValue(optionDto.getOptionValue());
                optionRepository.save(existingOption);
                existingOptionMap.remove(optionDto.getOptionId()); // 수정된 옵션은 삭제 목록에서 제거
            }
        }

        // 기존 옵션 중 수정되지 않고 남아 있는 옵션은 삭제
        optionRepository.deleteAll(existingOptionMap.values());
        questionRepository.flush();
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
}
