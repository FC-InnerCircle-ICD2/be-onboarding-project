package com.common.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class SurveyVo {
    private String name;
    private String items;

    public SurveyVo ( String name, String items) {
        this.name = name;
        this.items = items;
    }
    public SurveyVo() {
        // Default constructor
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}


