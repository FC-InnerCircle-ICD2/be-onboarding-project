package com.innercicle.application.port.in.v1;

import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.SurveyItem;
import com.innercicle.mapper.DomainMapper;
import com.innercicle.validation.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterSurveyItemCommandV1 extends SelfValidating<RegisterSurveyItemCommandV1> implements DomainMapper<SurveyItem> {

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
    private List<String> options;

    @Override
    public void validateSelf() {
        super.validateSelf();
        type.validateOptions(options);
    }

    @Builder(builderClassName = "RegisterSurveyItemCommandV1Builder", builderMethodName = "buildInternal")
    public static RegisterSurveyItemCommandV1 create(String item,
                                                     String description,
                                                     InputType type,
                                                     boolean required,
                                                     List<String> options) {
        return new RegisterSurveyItemCommandV1(item, description, type, required, options);
    }

    /**
     * builder 호출 이후 validation 체크를 위한 build 메소드
     */
    public static class RegisterSurveyItemCommandV1Builder {

        public RegisterSurveyItemCommandV1 build() {
            RegisterSurveyItemCommandV1 registerSurveyCommandV1 =
                create(item, description, type, required, options);
            registerSurveyCommandV1.validateSelf();
            return registerSurveyCommandV1;
        }

    }

    @Override
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
