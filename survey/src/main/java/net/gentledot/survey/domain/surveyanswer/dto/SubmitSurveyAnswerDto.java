package net.gentledot.survey.domain.surveyanswer.dto;

import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.application.service.in.model.request.SubmitSurveyAnswer;

import java.util.List;

@ToString
@Getter
public class SubmitSurveyAnswerDto {
    private final Long questionId;
    private final List<String> answer;

    private SubmitSurveyAnswerDto(Long questionId, List<String> answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public static SubmitSurveyAnswerDto from(SubmitSurveyAnswer submitSurveyAnswer) {
        return new SubmitSurveyAnswerDto(
                submitSurveyAnswer.getQuestionId(),
                submitSurveyAnswer.getAnswer());
    }
}
