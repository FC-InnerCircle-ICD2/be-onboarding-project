package org.innercircle.surveyapiapplication.domain.question.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@SuperBuilder
@Getter
public class Question {

    private Long id;
    private String name;
    private String description;
    private boolean required;
    private Long surveyId;

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
