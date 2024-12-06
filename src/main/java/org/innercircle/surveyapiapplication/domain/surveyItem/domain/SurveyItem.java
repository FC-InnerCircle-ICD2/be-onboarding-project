package org.innercircle.surveyapiapplication.domain.surveyItem.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.type.SurveyItemType;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

import java.util.List;
import java.util.Objects;

@Getter
public abstract class SurveyItem {

    private Long id;
    private int version;

    private String name;
    private String description;
    private boolean required;
    private Long surveyId;

    public SurveyItem(Long id, int version, String name, String description, boolean required, Long surveyId) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.required = required;
        this.surveyId = surveyId;
    }

    public SurveyItem(Long id, int version, String name, String description, boolean required) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.description = description;
        this.required = required;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public SurveyItem update(String name, String description, SurveyItemType type, boolean required, List<String> options) {
        SurveyItem updatedSurveyItem = switch (type) {
            case TEXT -> new TextSurveyItem(this.id, this.version, name, description, required, this.surveyId);
            case PARAGRAPH -> new ParagraphSurveyItem(this.id, this.version, name, description, required, this.surveyId);
            case SINGLE_CHOICE_ANSWER -> new SingleChoiceSurveyItem(this.id, this.version, name, description, required, this.surveyId, options);
            case MULTI_CHOICE_ANSWER -> new MultiChoiceSurveyItem(this.id, this.version, name, description, required, this.surveyId, options);
            default -> throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
        };
        updatedSurveyItem.addVersion();
        return updatedSurveyItem;
    }

    private void addVersion() {
        this.version++;
    }

    public abstract SurveyItemType getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SurveyItem surveyItem = (SurveyItem) o;

        if (version != surveyItem.version) return false;
        return Objects.equals(id, surveyItem.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + version;
        return result;
    }

}
