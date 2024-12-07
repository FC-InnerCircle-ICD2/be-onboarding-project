package org.survey.domain.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveySearchRequest {

    @NotNull
    private Long surveyId;

    private String itemName;

    private String replyContent;
}
