package com.innercicle.application.port.in.v1;

import com.innercicle.SelfValidating;
import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.SurveyItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSurveyItemCommandV1 extends SelfValidating<RegisterSurveyItemCommandV1> {

    /**
     * 설문 항목 명
     */
    @NotNull(message = "설문 항목 명은 필수 입니다.")
    private String item;
    /**
     * 설문 항목 설명
     */
    @NotNull(message = "설문 항목 설명은 필수 입니다.")
    private String description;
    /**
     * 설문 항목 타입
     */
    @NotNull(message = "설문 항목 타입은 필수 입니다.")
    private InputType type;

    /**
     * 필수 여부
     */
    private boolean required;

    /**
     * 설문 항목 선택지 목록
     */
    @NotNull(message = "설문 항목 선택지 목록은 필수 입니다.")
    private List<String> options;

    public SurveyItem mapToDomain() {
        return SurveyItem.builder()
            .item(item)
            .description(description)
            .inputType(type)
            .required(required)
            .options(options)
            .build();
    }

}
