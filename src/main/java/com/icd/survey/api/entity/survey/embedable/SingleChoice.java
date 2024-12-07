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
public class SingleChoice {
    @Column(name = "single_choice_option_seq")
    private Long optionSeq;
}
