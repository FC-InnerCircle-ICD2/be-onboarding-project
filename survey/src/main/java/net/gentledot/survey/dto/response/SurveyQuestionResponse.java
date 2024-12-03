package net.gentledot.survey.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyQuestionResponse {
    private Long questionId;
    private String question;
    private String description;
    private SurveyItemType type;
    private ItemRequired required;
    private List<SurveyQuestionOptionResponse> options;

    public static SurveyQuestionResponse from(SurveyQuestion surveyQuestion) {
        return SurveyQuestionResponse.builder()
                .questionId(surveyQuestion.getId())
                .question(surveyQuestion.getItemName())
                .description(surveyQuestion.getItemDescription())
                .type(surveyQuestion.getItemType())
                .required(surveyQuestion.getRequired()).options(surveyQuestion.getOptions().stream()
                        .map(option -> new SurveyQuestionOptionResponse(option.getOptionText()))
                        .collect(Collectors.toList()))
                .build();
    }
}
