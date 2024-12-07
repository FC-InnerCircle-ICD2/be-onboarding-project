package com.innercicle.application.port.in.v1;

import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.SurveyItem;
import com.innercicle.validation.SelfValidating;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifySurveyItemCommandV1 extends SelfValidating<ModifySurveyItemCommandV1> {

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

    @Override
    public void validateSelf() {
        super.validateSelf();
        type.validateOptions(this.options);
    }

    @Builder(builderClassName = "ModifySurveyItemCommandV1Builder", builderMethodName = "buildInternal")
    public static ModifySurveyItemCommandV1 create(Long id,
                                                   String item,
                                                   String description,
                                                   InputType type,
                                                   boolean required,
                                                   List<String> options) {
        return new ModifySurveyItemCommandV1(id, item, description, type, required, options);
    }

    public static class ModifySurveyItemCommandV1Builder {

        public ModifySurveyItemCommandV1 build() {
            ModifySurveyItemCommandV1 modifySurveyItemCommandV1 =
                create(id, item, description, type, required, options);
            modifySurveyItemCommandV1.validateSelf();
            return modifySurveyItemCommandV1;
        }

    }

    public SurveyItem mapToDomain() {
        return SurveyItem.builder()
            .id(id)
            .item(item)
            .description(description)
            .inputType(type)
            .required(required)
            .options(options)
            .build();
    }

}
