package org.innercircle.surveyapiapplication.domain.survey.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Survey {

    private Long id;
    private String name;
    private String description;
    private List<Question> questions = new ArrayList<>();

    public Survey(Long id, String name, String description, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public Survey(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Survey(String name, String description, List<Question> questions) {
        this.name = name;
        this.description = description;
        this.addQuestions(questions);
    }

    public Survey(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Survey addQuestions(List<Question> questions) {
        if (this.questions.size() + questions.size() > 10) {
            throw new CustomException(CustomResponseStatus.QUESTION_SIZE_FULL);
        }
        questions.forEach(this::addQuestion);
        return this;
    }

    public Survey addQuestion(Question question) {
        if (this.questions.size() >= 10) {
            throw new CustomException(CustomResponseStatus.QUESTION_SIZE_FULL);
        }
        questions.add(question);
        question.setSurveyId(this.getId());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Survey survey = (Survey) o;

        return Objects.equals(id, survey.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
