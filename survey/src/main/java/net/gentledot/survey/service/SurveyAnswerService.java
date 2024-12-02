package net.gentledot.survey.service;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.dto.request.SearchSurveyAnswerRequest;
import net.gentledot.survey.dto.request.SubmitSurveyAnswer;
import net.gentledot.survey.dto.response.SearchSurveyAnswerResponse;
import net.gentledot.survey.dto.response.SurveyAnswerValue;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveyNotFoundException;
import net.gentledot.survey.exception.SurveySubmitValidationException;
import net.gentledot.survey.model.entity.surveyanswer.SurveyAnswer;
import net.gentledot.survey.model.entity.surveyanswer.SurveyAnswerSubmission;
import net.gentledot.survey.model.entity.surveybase.Survey;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;
import net.gentledot.survey.repository.SurveyAnswerRepository;
import net.gentledot.survey.repository.SurveyRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SurveyAnswerService {
    private final SurveyRepository surveyRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;

    public SurveyAnswerService(SurveyRepository surveyRepository, SurveyAnswerRepository surveyAnswerRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyAnswerRepository = surveyAnswerRepository;
    }

    @Transactional
    public void submitSurveyAnswer(String surveyId, List<SubmitSurveyAnswer> answers) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND));

        // 설문조사 항목과 응답 값 검증
        validateSurveyAnswers(survey, answers);

        SurveyAnswer surveyAnswer = SurveyAnswer.of(survey, answers);
        surveyAnswerRepository.save(surveyAnswer);
    }

    @Transactional(readOnly = true)
    public SearchSurveyAnswerResponse getSurveyAnswers(SearchSurveyAnswerRequest request) {
        String surveyId = request.getSurveyId();
        Optional<Survey> targetSurvey = surveyRepository.findById(surveyId);
        if (targetSurvey.isEmpty()) {
            throw new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND);
        }

        List<SurveyAnswer> allSurveyAnswers = surveyAnswerRepository.findAllBySurveyId(surveyId);

        List<SurveyAnswerValue> answerValues = allSurveyAnswers.stream()
                .map(surveyAnswer -> {
                    List<SurveyAnswerSubmission> answers = surveyAnswer.getAnswers();
                    return SurveyAnswerValue.of(surveyAnswer.getId(), answers);
                })
                .collect(Collectors.toList());

        return new SearchSurveyAnswerResponse(surveyId, answerValues);
    }

    public void validateSurveyAnswers(Survey survey, List<SubmitSurveyAnswer> answers) {
        Map<Long, SurveyQuestion> questionMap = survey.getQuestions().stream()
                .collect(Collectors.toMap(SurveyQuestion::getId, question -> question));

        for (SubmitSurveyAnswer answer : answers) {
            // 1. questionId가 유효한지 확인
            if (!questionMap.containsKey(answer.getQuestionId())) {
                throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_ID);
            }

            SurveyQuestion question = questionMap.get(answer.getQuestionId());

            // 2. questionOptionId가 유효한지 확인
            if (question.getItemType() == SurveyItemType.SINGLE_SELECT || question.getItemType() == SurveyItemType.MULTI_SELECT) {
                boolean optionExists = question.getOptions().stream()
                        .anyMatch(option -> option.getId().equals(answer.getQuestionOptionId()));
                if (!optionExists) {
                    throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_OPTION_ID);
                }
            }

            // 3. answer가 비어 있는지 확인 (필수 항목)
            if (question.getRequired() == ItemRequired.REQUIRED) {
                if (SurveyItemType.SINGLE_SELECT.equals(question.getItemType()) ||
                    SurveyItemType.MULTI_SELECT.equals(question.getItemType())) {
                    if (answer.getQuestionOptionId() == null) {
                        throw new SurveySubmitValidationException(ServiceError.BAD_REQUEST);
                    }
                } else {
                    if (StringUtils.isEmpty(answer.getAnswer())) {
                        throw new SurveySubmitValidationException(ServiceError.BAD_REQUEST);
                    }
                }
            }

            // 4. SINGLE_SELECT 타입 - option이 없거나 2개 이상인 경우 확인
            if (question.getItemType() == SurveyItemType.SINGLE_SELECT) {
                long optionCount = answers.stream()
                        .filter(a -> a.getQuestionId().equals(answer.getQuestionId()))
                        .count();
                if (optionCount != 1) {
                    throw new SurveySubmitValidationException(ServiceError.BAD_REQUEST);
                }
            }
        }
    }
}
