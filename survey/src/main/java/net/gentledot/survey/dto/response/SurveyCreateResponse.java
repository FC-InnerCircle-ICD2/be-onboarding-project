package net.gentledot.survey.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import lombok.ToString;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
public final class SurveyCreateResponse {
    private final String surveyId;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    private final List<SurveyQuestionResponse> questions;

    private SurveyCreateResponse(String surveyId, LocalDateTime createdAt, List<SurveyQuestionResponse> questions) {
        this.surveyId = surveyId;
        this.createdAt = createdAt;
        this.questions = questions;
    }

    public static SurveyCreateResponse of(String surveyId, LocalDateTime createdAt, List<SurveyQuestion> questions) {
        List<SurveyQuestionResponse> surveyQuestionResponses = questions.stream()
                .map(SurveyQuestionResponse::from)
                .collect(Collectors.toList());
        return new SurveyCreateResponse(surveyId, createdAt, surveyQuestionResponses);
    }
}
