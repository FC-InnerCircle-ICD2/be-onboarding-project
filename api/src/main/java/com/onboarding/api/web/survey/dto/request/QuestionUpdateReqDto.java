package com.onboarding.api.web.survey.dto.request;

import com.onboarding.survey.enums.Operation;
import com.onboarding.survey.enums.QuestionType;
import java.util.List;

public record QuestionUpdateReqDto(
    String title,
    String description,
    QuestionType type,
    boolean isRequired,
    List<String> choices
) {

}
