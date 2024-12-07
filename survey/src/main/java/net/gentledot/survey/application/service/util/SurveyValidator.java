package net.gentledot.survey.application.service.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.gentledot.survey.application.service.in.model.request.SubmitSurveyAnswer;
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionRequest;
import net.gentledot.survey.application.service.in.model.request.SurveyRequest;
import net.gentledot.survey.domain.enums.ItemRequired;
import net.gentledot.survey.domain.enums.SurveyItemType;
import net.gentledot.survey.domain.enums.UpdateType;
import net.gentledot.survey.domain.exception.ServiceError;
import net.gentledot.survey.domain.exception.SurveyCreationException;
import net.gentledot.survey.domain.exception.SurveySubmitValidationException;
import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyValidator {

    public static final int MAXIMUM_QUESTION_COUNT = 10;

    public static void validateSurveyAnswers(Survey survey, List<SubmitSurveyAnswer> answers) {
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

    private static boolean isValidAnswer(SurveyQuestion question, SubmitSurveyAnswer answer) {
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

    public static void validateRequest(SurveyRequest surveyRequest) {
        List<SurveyQuestionRequest> questions = surveyRequest.getQuestions();

        if (questions == null) {
            throw new SurveyCreationException(ServiceError.CREATION_INVALID_REQUEST);
        } else if (questions.isEmpty() || questions.size() > MAXIMUM_QUESTION_COUNT) {
            throw new SurveyCreationException(ServiceError.CREATION_INSUFFICIENT_QUESTIONS);
        }

        Map<Long, Long> questionCountMap = new HashMap<>();
        for (SurveyQuestionRequest question : questions) {
            if (question.getUpdateType() == null || question.getUpdateType().equals(UpdateType.MODIFY)) {


                List<SurveyQuestionOptionRequest> questionOptions = question.getOptions();
                if (SurveyItemType.SINGLE_SELECT.equals(question.getType()) || SurveyItemType.MULTI_SELECT.equals(question.getType())) {
                    if (questionOptions == null || questionOptions.isEmpty()) {
                        throw new SurveyCreationException(ServiceError.CREATION_REQUIRED_OPTIONS);
                    }
                }

                Long questionId = question.getQuestionId();
                if (questionId != null) {
                    questionCountMap.put(
                            questionId,
                            questionCountMap.getOrDefault(questionId, 0L) + 1
                    );
                }

                questionCountMap.entrySet().stream()
                        .filter(entry -> entry.getValue() > 1)
                        .findFirst()
                        .ifPresent(entry -> {
                            throw new SurveyCreationException(ServiceError.CREATION_DUPLICATE_QUESTIONS);
                        });
            } else {
                if (question.getQuestionId() == null) {
                    throw new SurveyCreationException(ServiceError.CREATION_REQUIRED_OPTIONS);
                }
            }
        }
    }
}
