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
import net.gentledot.survey.model.entity.common.BaseEntity;

import java.util.List;
import java.util.Objects;
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

    public void updateSurvey(String name, String description, List<SurveyQuestion> surveyQuestions) {
        this.name = name;
        this.description = description;
        disconnectRelationFromSurvey(); // update 시 Survey와의 관계 끊기
        this.questions.clear();
        this.questions.addAll(surveyQuestions);
        surveyQuestions.forEach(surveyQuestion -> {
            surveyQuestion.setSurvey(this);
            surveyQuestion.getOptions().forEach(option -> option.setSurveyQuestion(surveyQuestion));
        });
    }

    private void disconnectRelationFromSurvey() {
        this.questions.forEach(question -> question.setSurvey(null));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return Objects.equals(id, survey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

