package com.innercicle.application.port.in.v1;

import com.innercicle.SelfValidating;
import com.innercicle.domain.v1.Survey;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifySurveyCommandV1 extends SelfValidating<ModifySurveyCommandV1> {

    private Long id;

    private String name;

    private String description;

    private List<ModifySurveyItemCommandV1> items;

    @Builder(builderClassName = "ModifySurveyCommandV1Builder", builderMethodName = "buildInternal")
    public static ModifySurveyCommandV1 create(Long id, String name, String description, List<ModifySurveyItemCommandV1> items) {
        return new ModifySurveyCommandV1(id, name, description, items);
    }

    public Survey mapToDomain() {
        return Survey.builder()
            .id(id)
            .name(name)
            .description(description)
            .items(items.stream()
                       .map(ModifySurveyItemCommandV1::mapToDomain)
                       .toList())
            .build();
    }

    public static class ModifySurveyCommandV1Builder {

        public ModifySurveyCommandV1 build() {
            ModifySurveyCommandV1 modifySurveyCommandV1 =
                create(id, name, description, items);
            modifySurveyCommandV1.validateSelf();
            return modifySurveyCommandV1;
        }

    }

}
