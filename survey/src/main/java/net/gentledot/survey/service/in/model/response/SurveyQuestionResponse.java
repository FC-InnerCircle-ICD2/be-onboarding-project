package net.gentledot.survey.service.in.model.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.domain.enums.ItemRequired;
import net.gentledot.survey.domain.enums.SurveyItemType;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;

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
