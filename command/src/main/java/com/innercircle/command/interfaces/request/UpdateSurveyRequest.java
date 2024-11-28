package com.innercircle.command.interfaces.request;

import com.innercircle.command.application.survey.question.QuestionUpdateInput;
import java.util.List;

public record UpdateSurveyRequest(List<QuestionUpdateInput> updateInputs) {

}
