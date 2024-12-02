package org.survey.api.domain.survey.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.survey.db.BaseStatus;
import org.survey.db.surveyitem.ItemInputType;

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

    private BaseStatus status;

    private LocalDateTime registeredAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime unregisteredAt;
}
