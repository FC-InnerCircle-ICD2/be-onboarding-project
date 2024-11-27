package org.innercircle.surveyapiapplication.domain.survey.domain;

import lombok.Builder;
import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomExceptionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class Survey {

    private Long id;
    private String name;
    private String description;

    @Builder.Default
    private List<Question> questions = new ArrayList<>();

    public Survey addQuestion(Question question) {
        if (this.questions.size() >= 10) {
            throw new CustomException(CustomExceptionStatus.QUESTION_SIZE_FULL);
        }
        question.setSurveyId(this.id);
        questions.add(question);
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

}
