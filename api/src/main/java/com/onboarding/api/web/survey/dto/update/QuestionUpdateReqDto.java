package com.onboarding.api.web.survey.dto.update;

import com.onboarding.survey.survey.enums.Operation;
import com.onboarding.survey.survey.enums.QuestionType;
import java.util.List;

public record QuestionUpdateReqDto(
    Long id,
    String title,
    String description,
    QuestionType type,
    Operation operation,
    Integer orderIndex,
    boolean isRequired,
    List<String> choices
) {

}
