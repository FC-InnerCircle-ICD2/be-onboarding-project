package org.innercircle.surveyapiapplication.domain.question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.global.entity.BaseEntity;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
public class QuestionEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 1000, nullable = true)
    private String description;

    @Column(nullable = false)
    private boolean required;

    @Column(name = "survey_id", nullable = false)
    private Long surveyId;

    public QuestionEntity(String name, String description, boolean required, Long surveyId) {
        this.name = name;
        this.description = description;
        this.required = required;
        this.surveyId = surveyId;
    }

    public static QuestionEntity from(Question question) {
        return new QuestionEntity(question.getName(), question.getDescription(), question.isRequired(), question.getSurveyId());
    }

    public Question toDomain() {
        return Question.builder()
            .id(this.id)
            .name(this.name)
            .description(this.description)
            .required(this.required)
            .surveyId(this.surveyId)
            .build();
    }

}
