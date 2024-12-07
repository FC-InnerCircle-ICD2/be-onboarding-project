package com.survey.api.dto;

import com.survey.api.util.DateUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyResponseDto {
    private Long id;
    private String name;
    private String description;
    private String regDtm;
    private String useYn;

    public SurveyResponseDto(Long id, String name, String description, LocalDateTime regDtm, String useYn) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.regDtm = DateUtil.getDateTimeString(regDtm);
        this.useYn = useYn;
    }
}
