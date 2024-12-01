package com.innercicle.adapter.in.web.survey.v1.request;

import com.innercicle.application.port.in.v1.RegisterSurveyItemCommandV1;
import com.innercicle.domain.v1.InputType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSurveyItemRequestV1 {

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

    public RegisterSurveyItemCommandV1 mapToCommand() {
        return RegisterSurveyItemCommandV1.builder()
            .item(this.item)
            .description(this.description)
            .type(this.type)
            .required(this.required)
            .options(this.options)
            .build();
    }

}

