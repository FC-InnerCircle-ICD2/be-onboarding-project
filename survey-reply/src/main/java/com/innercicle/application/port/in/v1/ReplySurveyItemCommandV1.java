package com.innercicle.application.port.in.v1;

import com.innercicle.domain.InputType;
import com.innercicle.validation.SelfValidating;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplySurveyItemCommandV1 extends SelfValidating<ReplySurveyItemCommandV1> {

    private Long id;

    private String item;

    private String description;

    private InputType inputType;

    private boolean required;

    private List<ItemOptionCommandV1> options;

}
