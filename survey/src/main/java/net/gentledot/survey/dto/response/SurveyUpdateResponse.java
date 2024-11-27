package net.gentledot.survey.dto.response;

import java.time.LocalDateTime;

public record SurveyUpdateResponse(
        String surveyId,
        LocalDateTime updatedAt
) {
}
