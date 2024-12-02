package org.survey.api.domain.survey.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyReplyListResponse {

    private Long id;

    private List<SurveyReplyResponse> reply;
}
