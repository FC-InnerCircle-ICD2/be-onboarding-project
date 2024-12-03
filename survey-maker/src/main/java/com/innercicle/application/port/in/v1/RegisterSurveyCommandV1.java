package com.innercicle.application.port.in.v1;

import com.innercicle.domain.v1.Survey;
import com.innercicle.validation.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterSurveyCommandV1 extends SelfValidating<RegisterSurveyCommandV1> {

    @NotNull(message = "설문 명은 필수 입니다.")
    private String name;

    @NotNull(message = "설문 설명은 필수 입니다.")
    private String description;

    @NotNull(message = "설문 항목 목록은 필수 입니다.")
    private List<RegisterSurveyItemCommandV1> items;

    @Builder(builderClassName = "RegisterSurveyCommandV1Builder", builderMethodName = "buildInternal")
    public static RegisterSurveyCommandV1 create(String name, String description, List<RegisterSurveyItemCommandV1> items) {
        return new RegisterSurveyCommandV1(name, description, items);
    }

    /**
     * builder 호출 이후 validation 체크를 위한 build 메소드
     */
    public static class RegisterSurveyCommandV1Builder {

        public RegisterSurveyCommandV1 build() {
            RegisterSurveyCommandV1 registerSurveyCommandV1 =
                create(name, description, items);
            registerSurveyCommandV1.validateSelf();
            return registerSurveyCommandV1;
        }

    }

    public Survey mapToDomain() {
        return Survey.builder()
            .name(name)
            .description(description)
            .items(items.stream()
                       .map(RegisterSurveyItemCommandV1::mapToDomain)
                       .toList())
            .build();
    }

}
