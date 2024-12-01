package com.innercicle.application.port.in.v1;

import com.innercicle.SelfValidating;
import com.innercicle.advice.exceptions.RequiredFieldException;
import com.innercicle.domain.v1.InputType;
import com.innercicle.domain.v1.SurveyItem;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Override
    public void validateSelf() {
        super.validateSelf();
        if ((type == InputType.MULTI_SELECT || type == InputType.SINGLE_SELECT)
            && (options == null || options.isEmpty()
            || options.size() < 2)) {
            throw new RequiredFieldException(this.type.getType() + "일 경우 선택지는 2개 이상 입력해 주세요.");
        }
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
