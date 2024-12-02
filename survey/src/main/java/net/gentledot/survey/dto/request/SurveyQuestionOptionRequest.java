package net.gentledot.survey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.model.enums.AnswerType;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestionOptionRequest {
    private String optionText;
    private AnswerType answerType;
}
