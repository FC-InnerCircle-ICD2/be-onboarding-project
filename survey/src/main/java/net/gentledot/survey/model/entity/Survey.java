package net.gentledot.survey.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)@Getter
@ToString
public class Survey {
    private String name;
    private String description;
    private List<SurveyItem> item;

    public static Survey of(String name, String description, List<SurveyItem> surveyItems) {
        return new Survey(name, description, surveyItems);
    }
}

