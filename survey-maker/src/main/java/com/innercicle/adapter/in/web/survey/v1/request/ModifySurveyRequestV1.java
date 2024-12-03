package com.innercicle.adapter.in.web.survey.v1.request;

import com.innercicle.application.port.in.v1.ModifySurveyCommandV1;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
public class ModifySurveyRequestV1 {

    /**
     * 설문 ID
     */
    private Long id;

    /**
     * 설문 명
     */
    private String name;

    /**
     * 설문 설명
     */
    private String description;

    /**
     * 설문 항목 목록
     */
    private List<ModifySurveyItemRequestV1> items;

    public ModifySurveyCommandV1 mapToCommand() {
        return ModifySurveyCommandV1.builder()
            .id(this.id)
            .name(this.name)
            .description(this.description)
            .items(!CollectionUtils.isEmpty(this.items) ? this.items.stream()
                .map(ModifySurveyItemRequestV1::mapToCommand)
                .toList() : null)
            .build();
    }

}
