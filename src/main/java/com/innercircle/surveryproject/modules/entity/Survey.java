package com.innercircle.surveryproject.modules.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innercircle.surveryproject.modules.dto.SurveyCreateDto;
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

    @JsonIgnore
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

}
