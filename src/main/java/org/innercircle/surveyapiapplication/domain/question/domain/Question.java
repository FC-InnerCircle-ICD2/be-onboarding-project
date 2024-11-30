package org.innercircle.surveyapiapplication.domain.question.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;

import java.util.Objects;

@Getter
public abstract class Question {

    private Long id;
    private String name;
    private String description;
    private boolean required;
    private Long surveyId;

    public Question(Long id, String name, String description, boolean required, Long surveyId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.required = required;
        this.surveyId = surveyId;
    }

    public Question(Long id, String name, String description, boolean required) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.required = required;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public abstract void answer(Answer answer);

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
