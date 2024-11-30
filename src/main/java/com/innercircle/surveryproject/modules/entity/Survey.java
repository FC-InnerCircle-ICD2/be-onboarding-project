package com.innercircle.surveryproject.modules.entity;

import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
import com.innercircle.surveryproject.modules.dto.SurveyUpdateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Survey {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Setter
    @OneToMany(mappedBy = "survey")
    private List<SurveyItem> surveyItemList;

    public Survey(SurveyCreateDto surveyCreateDto) {
        this.name = surveyCreateDto.getName();
        this.description = surveyCreateDto.getDescription();
    }

    public static Survey from(SurveyCreateDto surveyCreateDto) {
        return new Survey(surveyCreateDto);
    }

    public void update(SurveyUpdateDto surveyUpdateDto) {
        this.name = surveyUpdateDto.getName();
        this.description = surveyUpdateDto.getDescription();
        this.surveyItemList = SurveyItem.convertToEntity(surveyUpdateDto.getSurveyItemDtoList());
    }

}
