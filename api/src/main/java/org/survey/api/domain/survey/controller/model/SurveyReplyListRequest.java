package org.survey.api.domain.survey.controller.model;

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
public class SurveyReplyListRequest {
    @NotNull
    private Long id;

    private List<SurveyReplyRequest> reply;
}
