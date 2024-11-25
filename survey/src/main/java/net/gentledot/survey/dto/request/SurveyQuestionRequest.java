package net.gentledot.survey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;

import java.util.List;

@Builder
@ToString
@Getter
@AllArgsConstructor
public class SurveyQuestionRequest {
    private String itemName;
    private String itemDescription;
    private SurveyItemType itemType;
    private ItemRequired required;
    private List<SurveyQuestionOptionRequest> options;
}
