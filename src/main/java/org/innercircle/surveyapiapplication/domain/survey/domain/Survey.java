package org.innercircle.surveyapiapplication.domain.survey.domain;

import lombok.Getter;
import org.innercircle.surveyapiapplication.domain.surveyItem.domain.SurveyItem;
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
    private List<SurveyItem> surveyItems = new ArrayList<>();

    public Survey(Long id, String name, String description, List<SurveyItem> surveyItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.surveyItems = surveyItems;
    }

    public Survey(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Survey(String name, String description, List<SurveyItem> surveyItems) {
        this.name = name;
        this.description = description;
        this.addSurveyItems(surveyItems);
    }

    public Survey(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Survey addSurveyItems(List<SurveyItem> surveyItems) {
        if (this.surveyItems.size() + surveyItems.size() > 10) {
            throw new CustomException(CustomResponseStatus.SURVEY_ITEM_SIZE_FULL);
        }
        surveyItems.forEach(this::addSurveyItem);
        return this;
    }

    public Survey addSurveyItem(SurveyItem surveyItem) {
        if (this.surveyItems.size() >= 10) {
            throw new CustomException(CustomResponseStatus.SURVEY_ITEM_SIZE_FULL);
        }
        surveyItems.add(surveyItem);
        surveyItem.setSurveyId(this.getId());
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
