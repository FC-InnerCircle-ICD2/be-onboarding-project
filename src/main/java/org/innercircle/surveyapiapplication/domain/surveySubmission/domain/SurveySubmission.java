package org.innercircle.surveyapiapplication.domain.surveySubmission.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;

import java.util.Objects;

@Getter
public abstract class SurveySubmission<T> {

    private Long id;
    protected Long surveyItemId;
    protected int surveyItemVersion;
    private T response;

    public SurveySubmission(Long id, Long surveyItemId, int surveyItemVersion, T response) {
        this.id = id;
        this.surveyItemId = surveyItemId;
        this.surveyItemVersion = surveyItemVersion;
        this.response = response;
    }

    public SurveySubmission(T response) {
        this.response = response;
    }

    public abstract SurveySubmission<T> setSurveyItemIdAndVersion(SurveyItem surveyItem);

    public void changeResponse(T response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveySubmission<?> that = (SurveySubmission<?>) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
