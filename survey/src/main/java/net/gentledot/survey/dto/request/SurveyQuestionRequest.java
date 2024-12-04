package net.gentledot.survey.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.dto.enums.UpdateType;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;

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
