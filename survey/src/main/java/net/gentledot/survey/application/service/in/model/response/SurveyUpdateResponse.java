package net.gentledot.survey.application.service.in.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public final class SurveyUpdateResponse {
    private final String surveyId;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;
    private final List<SurveyQuestionResponse> questions;

    private SurveyUpdateResponse(String surveyId, LocalDateTime updatedAt, List<SurveyQuestionResponse> questions) {
        this.surveyId = surveyId;
        this.updatedAt = updatedAt;
        this.questions = questions;
    }

    public static SurveyUpdateResponse of(String surveyId, LocalDateTime updatedAt, List<SurveyQuestion> questions) {
        List<SurveyQuestionResponse> surveyQuestionResponses = questions.stream()
                .map(SurveyQuestionResponse::from)
                .collect(Collectors.toList());
        return new SurveyUpdateResponse(surveyId, updatedAt, surveyQuestionResponses);
    }
}
