package com.icd.survey.api.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemOptionRequest {
    private String option;
}
