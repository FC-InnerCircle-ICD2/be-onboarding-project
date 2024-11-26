package net.gentledot.survey.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "questions")
@Entity
public class Survey extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "survey")
    private List<SurveyQuestion> questions;

    public static Survey of(String name, String description, List<SurveyQuestion> surveyQuestions) {
        String surveyId = UUID.randomUUID().toString();
        Survey survey = new Survey(surveyId, name, description, surveyQuestions);
        surveyQuestions.forEach(surveyQuestion -> {
            surveyQuestion.setSurvey(survey);
            surveyQuestion.getOptions().forEach(option -> option.setSurveyQuestion(surveyQuestion));
        });
        return survey;
    }
}

