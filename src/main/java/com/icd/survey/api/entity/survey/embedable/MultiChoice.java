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
public class MultiChoice {
    @Column(name = "multi_choice_option_seq")
    private Long multiOptionSeq;
}
