package com.innercicle.adapter.out.persistence.v1;

import com.innercicle.domain.InputType;
import com.innercicle.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplySurveyItemEntity {

    @Id
    @IdGenerator
    @Column(name = "reply_survey_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_survey_id")
    private ReplySurveyEntity replySurvey;

    private String item;

    private String description;

    @Enumerated(EnumType.STRING)
    private InputType inputType;

    /**
     * 응답 값
     */
    private String replyInput;

    private boolean required;

    @ElementCollection
    @CollectionTable(name = "reply_survey_item_option", joinColumns = @JoinColumn(name = "reply_survey_item_id"))
    private List<ItemOptionEntity> options;

}
