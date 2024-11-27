package com.innercircle.command.interfaces.request;

import com.innercircle.command.application.survey.question.QuestionInput;
import java.util.List;

public record CreateSurveyRequest(String name, String description, List<QuestionInput> questionInputs) {

}
