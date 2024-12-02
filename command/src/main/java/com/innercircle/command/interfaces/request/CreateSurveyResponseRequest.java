package com.innercircle.command.interfaces.request;

import com.innercircle.command.application.survey.response.QuestionResponseInput;
import java.util.List;

public record CreateSurveyResponseRequest(List<QuestionResponseInput> responseInputs) {

}
