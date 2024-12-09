package org.survey.domain.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.survey.domain.service.surveyitem.ItemInputType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyItemRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private ItemInputType inputType;

    @NotNull
    private Boolean required;

    private List<SelectOptionRequest> selectOptions;
}
