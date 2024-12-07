package com.icd.survey.api.entity.survey.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShortAnswer {
    @Column(name = "short_answer")
    private String shortAnswer;
}
