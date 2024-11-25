package net.gentledot.survey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class SurveyQuestionOptionRequest {
    private String optionText;
}
