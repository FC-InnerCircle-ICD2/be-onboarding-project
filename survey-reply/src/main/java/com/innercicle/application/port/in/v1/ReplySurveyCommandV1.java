package com.innercicle.application.port.in.v1;

import com.innercicle.validation.SelfValidating;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplySurveyCommandV1 extends SelfValidating<ReplySurveyCommandV1> {

    private Long surveyId;
    private String name;
    private String replierEmail;
    private String description;
    private List<ReplySurveyItemCommandV1> items;

}
