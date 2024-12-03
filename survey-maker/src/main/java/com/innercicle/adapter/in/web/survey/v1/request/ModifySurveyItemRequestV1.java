package com.innercicle.adapter.in.web.survey.v1.request;

import com.innercicle.application.port.in.v1.ModifySurveyItemCommandV1;
import com.innercicle.domain.v1.InputType;
import lombok.Getter;

import java.util.List;

@Getter
public class ModifySurveyItemRequestV1 {

    private Long id;

    /**
     * 설문 항목 명
     */
    private String item;

    /**
     * 설문 항목 설명
     */
    private String description;

    /**
     * 설문 항목 타입
     */
    private InputType type;

    /**
     * 필수 여부
     */
    private boolean required;

    /**
     * 설문 항목 선택지 목록
     */
    private List<String> options;

    public ModifySurveyItemCommandV1 mapToCommand() {
        return ModifySurveyItemCommandV1.builder()
            .id(this.id)
            .item(this.item)
            .description(this.description)
            .type(this.type)
            .required(this.required)
            .options(this.options)
            .build();
    }

}
