package org.survey.domain.service.surveyitem;

public enum ItemInputType {

    SHORT_ANSWER("단답형"),
    LONG_ANSWER("장문형"),
    SINGLE_SELECT_LIST("단일 선택 리스트"),
    MULTI_SELECT_LIST("다중 선택 리스트"),
    ;

    ItemInputType(String description){
        this.description = description;
    }
    private String description;
}
