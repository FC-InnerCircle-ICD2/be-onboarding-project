package com.icd.survey.api.entity.survey.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LongAnswer {
    @Column(name = "long_answer")
    private String longAnswer;
}
