package net.gentledot.survey.dto.response;

import java.time.LocalDateTime;

public record SurveyCreateResponse(
        String surveyId,
        LocalDateTime createdAt
) {
};
