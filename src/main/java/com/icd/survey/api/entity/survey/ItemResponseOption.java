package com.icd.survey.api.entity.survey;

import com.icd.survey.api.entity.dto.ItemResponseOptionDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "item_response_option")
public class ItemResponseOption {
    @Id
    @Column(name = "option_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionSeq;
    @Column(name = "option", nullable = false)
    private String option;

    @Setter
    @ManyToOne
    @JoinColumn(name = "item_seq")
    private SurveyItem surveyItem;

    public ItemResponseOptionDto of() {
        return ItemResponseOptionDto
                .builder()
                .optionSeq(this.optionSeq)
                .option(this.option)
                .build();
    }

}
