package com.onboarding.api.web.survey.dto.create;

import com.onboarding.survey.enums.QuestionType;
import java.util.List;

public record QuestionCreateReqDto(
    String title,
    String description,
    QuestionType type,
    boolean isRequired,
    Integer orderIndex,
    List<String> choices
) {

}
