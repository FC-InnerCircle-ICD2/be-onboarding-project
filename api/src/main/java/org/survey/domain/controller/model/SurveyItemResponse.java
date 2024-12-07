package org.survey.domain.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.survey.domain.service.BaseStatus;
import org.survey.domain.service.surveyitem.ItemInputType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyItemResponse {

    private Long id;

    private Long surveyId;

    private String name;

    private String description;

    private ItemInputType inputType;

    private Boolean required;

    private List<SelectOptionResponse> selectOptions;

    private List<SurveyReplyResponse> surveyReply;

    private BaseStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime unregisteredAt;
}
