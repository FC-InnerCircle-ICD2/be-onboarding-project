package com.survey.api.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="survey")
public class SurveyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String useYn;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<SurveyItemEntity> items; // 설문 항목 리스트목 리스트

    public SurveyEntity(String name, String description, String useYn) {
        this.name = name;
        this.description = description;
        this.useYn = useYn;
    }

    public SurveyEntity(Long id, String name, String description, String useYn) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.useYn = useYn;
    }
}
