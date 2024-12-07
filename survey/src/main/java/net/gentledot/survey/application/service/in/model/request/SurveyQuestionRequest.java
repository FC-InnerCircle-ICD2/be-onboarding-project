package net.gentledot.survey.application.service.in.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.domain.enums.ItemRequired;
import net.gentledot.survey.domain.enums.SurveyItemType;
import net.gentledot.survey.domain.enums.UpdateType;

import java.util.List;

@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestionRequest {
    private Long questionId;
    @JsonProperty("updateType")
    private UpdateType updateType;
    private String question;
    private String description;
    private SurveyItemType type;
    private ItemRequired required;
    private List<SurveyQuestionOptionRequest> options;
}
