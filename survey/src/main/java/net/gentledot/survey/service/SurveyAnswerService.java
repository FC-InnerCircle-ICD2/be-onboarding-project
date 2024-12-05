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

            // 2. answer가 비어 있는지 확인 (필수 항목)
            if (question.getRequired() == ItemRequired.REQUIRED) {
                if (answer.getAnswer().isEmpty() || StringUtils.isBlank(answer.getAnswer().getFirst())) {
                    throw new SurveySubmitValidationException(ServiceError.BAD_REQUEST);
                }

                if (SurveyItemType.SINGLE_SELECT.equals(question.getItemType()) && answer.getAnswer().size() > 1) {
                    throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_OPTION_ID);
                }
            }

            // 3. 응답 값이 Question에서 받을 수 있는지 확인
            if (!isValidAnswer(question, answer)) {
                throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_OPTION_ID);
            }

        }
    }

    private boolean isValidAnswer(SurveyQuestion question, SubmitSurveyAnswer answer) {
        // 응답 값 검증 로직
        return switch (question.getItemType()) {
            case TEXT, PARAGRAPH -> answer.getAnswer().size() == 1;
            case SINGLE_SELECT -> question.getOptions().stream()
                    .anyMatch(option -> option.getOptionText().equals(answer.getAnswer().getFirst()));
            case MULTI_SELECT -> answer.getAnswer().stream()
                    .allMatch(ans -> question.getOptions().stream()
                            .anyMatch(option -> option.getOptionText().equals(ans)));
            default -> false;
        };
    }
}
