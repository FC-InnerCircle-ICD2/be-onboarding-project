package ic2.onboarding.survey.validator;

import ic2.onboarding.survey.dto.AnswerInfo;
import ic2.onboarding.survey.dto.SurveyInfo;
import ic2.onboarding.survey.global.BizConstants;
import ic2.onboarding.survey.global.BizException;
import ic2.onboarding.survey.global.ErrorCode;
import ic2.onboarding.survey.global.InputType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubmissionValidator {

    public static void validate(Map<String, SurveyInfo.Question> surveyQuestionByName,
                                Map<String, AnswerInfo> answerByQuestionName) {

        surveyQuestionByName.forEach((name, question) -> {

            AnswerInfo answerInfo = answerByQuestionName.get(name);

            // 필수값 누락 확인
            if (question.getRequired() && answerInfo == null) {
                throw new BizException(ErrorCode.REQUIRED_ANSWER_OMISSION);
            } else if (!question.getRequired() && answerInfo == null) {
                return;
            }

            // 선택답변 확인
            InputType inputType = question.getInputType();
            if (inputType.isChoiceType()) {
                Set<String> choiceOptions = new HashSet<>(question.getChoiceOptions());

                if (inputType == InputType.SINGLE_CHOICE) {
                    checkSingleChoiceAnswer(choiceOptions, answerInfo.getSingleTextAnswer());
                } else if (inputType == InputType.MULTIPLE_CHOICE) {
                    checkMultipleChoiceAnswer(choiceOptions, answerInfo.getMultipleTextAnswer());
                }
            }

            // 답변 길이 확인
            switch (inputType) {
                case SHORT_ANSWER, LONG_ANSWER, SINGLE_CHOICE ->
                        checkTextAnswerLength(answerInfo.getSingleTextAnswer());
                case MULTIPLE_CHOICE -> answerInfo.getMultipleTextAnswer()
                        .forEach(SubmissionValidator::checkTextAnswerLength);
            }
        });
    }


    private static void checkTextAnswerLength(String answer) {

        int answerLength = StringUtils.length(answer);
        if (answerLength < BizConstants.MIN_TEXT_ANSWER_LENGTH || answerLength > BizConstants.MAX_TEXT_ANSWER_LENGTH) {
            throw new BizException(ErrorCode.NOT_VALIDATED_ANSWER_LENGTH);
        }
    }


    private static void checkSingleChoiceAnswer(Set<String> choiceOptions, String answer) {

        if (!choiceOptions.contains(answer)) {
            throw new BizException(ErrorCode.NOT_VALIDATED_CHOICE_OPTION);
        }
    }


    private static void checkMultipleChoiceAnswer(Set<String> choiceOptions, List<String> multipleTextAnswer) {

        if (CollectionUtils.isEmpty(multipleTextAnswer) || !choiceOptions.containsAll(multipleTextAnswer)) {
            throw new BizException(ErrorCode.NOT_VALIDATED_CHOICE_OPTION);
        }
    }
}
