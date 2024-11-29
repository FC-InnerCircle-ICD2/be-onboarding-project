package org.innercircle.surveyapiapplication.domain.answer.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Answer {

    private Long id;
    private Long questionId;
    private String response;

    public Answer(Long id, Long questionId, String response) {
        this.id = id;
        this.questionId = questionId;
        this.response = response;
    }

    public Answer(Long id, String response) {
        this.id = id;
        this.response = response;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void changeResponse(String response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
