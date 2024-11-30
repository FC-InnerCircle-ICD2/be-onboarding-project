package com.survey.api.dto;

import com.survey.api.util.DateUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyResponseItemDto {
    private Long id;
    private String itemName;
    private String description;
    private String answer;
    private String itemType;
    private boolean required;
    private String regDtm;
    private String useYn;

    public SurveyResponseItemDto(Long id, String itemName, String description, String answer, String itemType, boolean required, LocalDateTime regDtm, String useYn) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.answer = answer;
        this.itemType = itemType;
        this.required = required;
        this.regDtm = DateUtil.getDateTimeString(regDtm);
        this.useYn = useYn;
    }
}
