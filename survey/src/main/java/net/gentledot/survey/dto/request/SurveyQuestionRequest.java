package net.gentledot.survey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    private String question;
    private String description;
    private SurveyItemType type;
    private ItemRequired required;
    @Setter
    private List<SurveyQuestionOptionRequest> options;
}
