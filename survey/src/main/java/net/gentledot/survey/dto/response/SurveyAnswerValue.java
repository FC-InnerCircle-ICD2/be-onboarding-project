package net.gentledot.survey.dto.response;

import net.gentledot.survey.model.entity.SurveyAnswerSubmission;

public record SurveyAnswerValue(
        String questionName,
        String answerValue
) {
    public static SurveyAnswerValue from(SurveyAnswerSubmission submittedAnswer) {
        return new SurveyAnswerValue(
                submittedAnswer.getSurveyQuestion().getItemName(),
                submittedAnswer.getAnswer()
        );
    }
}
