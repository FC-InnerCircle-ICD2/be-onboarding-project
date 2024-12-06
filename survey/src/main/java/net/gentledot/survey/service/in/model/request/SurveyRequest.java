package net.gentledot.survey.service.in.model.request;

import java.util.List;

public interface SurveyRequest {
    List<SurveyQuestionRequest> getQuestions();

}
