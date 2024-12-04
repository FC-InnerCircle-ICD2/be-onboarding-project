package com.innercicle.adapter.out.persistence.v1;

import com.innercicle.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "reply_survey")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplySurveyEntity {

    @Id
    @IdGenerator
    @Column(name = "reply_survey_id")
    private Long id;

    /**
     * 설문 제목
     */
    private String name;

    /**
     * 설문 설명
     */
    private String description;

    @OneToMany(mappedBy = "replySurvey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<ReplySurveyItemEntity> items = new ArrayList<>();

}
