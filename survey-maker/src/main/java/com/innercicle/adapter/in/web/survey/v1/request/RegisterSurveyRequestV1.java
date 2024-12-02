package com.innercicle.adapter.in.web.survey.v1.request;

import com.innercicle.application.port.in.v1.RegisterSurveyCommandV1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSurveyRequestV1 {

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
    private List<RegisterSurveyItemRequestV1> items;

    public RegisterSurveyCommandV1 mapToCommand() {
        RegisterSurveyCommandV1 commandV1 = RegisterSurveyCommandV1.builder()
            .name(this.name)
            .description(this.description)
            .items(!CollectionUtils.isEmpty(this.items) ? this.items.stream()
                .map(RegisterSurveyItemRequestV1::mapToCommand)
                .toList() : null)
            .build();
        commandV1.validateSelf();
        return commandV1;
    }

}
