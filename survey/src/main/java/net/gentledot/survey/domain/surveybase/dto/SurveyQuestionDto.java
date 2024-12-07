package net.gentledot.survey.domain.surveybase.dto;

import lombok.Getter;
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionOptionRequest;
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionRequest;
import net.gentledot.survey.domain.enums.ItemRequired;
import net.gentledot.survey.domain.enums.SurveyItemType;
import net.gentledot.survey.domain.enums.UpdateType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SurveyQuestionDto {
    private final Long questionId;
    private final UpdateType updateType;
    private final String question;
    private final String description;
    private final SurveyItemType type;
    private final ItemRequired required;
    private final List<SurveyQuestionOptionDto> options;

    private SurveyQuestionDto(Long questionId, UpdateType updateType, String question, String description, SurveyItemType type, ItemRequired required, List<SurveyQuestionOptionDto> options) {
        this.questionId = questionId;
        this.updateType = updateType;
        this.question = question;
        this.description = description;
        this.type = type;
        this.required = required;
        this.options = options;
    }


    public static SurveyQuestionDto from(SurveyQuestionRequest request) {
        List<SurveyQuestionOptionRequest> targetOptions = request.getOptions();
        List<SurveyQuestionOptionDto> questOptionDtoList = Collections.emptyList();
        if (targetOptions != null) {
            questOptionDtoList = targetOptions.stream()
                    .map(SurveyQuestionOptionRequest::getOption)
                    .map(SurveyQuestionOptionDto::new)
                    .collect(Collectors.toList());
        }


        return new SurveyQuestionDto(
                request.getQuestionId(),
                request.getUpdateType(),
                request.getQuestion(),
                request.getDescription(),
                request.getType(),
                request.getRequired(),
                questOptionDtoList
        );
    }
}
