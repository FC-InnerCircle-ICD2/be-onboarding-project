package org.innercircle.surveyapiapplication.domain.question.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;
import org.innercircle.surveyapiapplication.domain.question.domain.type.QuestionType;

import java.util.Objects;

@Getter
public abstract class Question {

    private Long id;
    private int version;

    private String name;
    private String description;
    private boolean required;
    private Long surveyId;

    public Question(Long id, int version, String name, String description, boolean required, Long surveyId) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.required = required;
        this.surveyId = surveyId;
    }

    public Question(Long id, int version, String name, String description, boolean required) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.required = required;
    }

    public Question(Long id, String name, String description, boolean required) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.required = required;
    }

    public Question(String name, String description, boolean required) {
        this.name = name;
        this.description = description;
        this.required = required;
    }


    public void addVersion() {
        this.version++;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public abstract void answer(Answer answer);

    public abstract QuestionType getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (version != question.version) return false;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + version;
        return result;
    }

}
