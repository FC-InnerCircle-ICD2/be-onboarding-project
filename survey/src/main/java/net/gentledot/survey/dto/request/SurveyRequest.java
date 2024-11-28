package net.gentledot.survey.dto.request;

import java.util.List;

public interface SurveyRequest {
    List<SurveyQuestionRequest> getQuestions();

}
