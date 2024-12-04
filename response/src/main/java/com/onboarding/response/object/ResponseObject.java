package com.onboarding.response.object;

import com.onboarding.response.entity.Answer;
import com.onboarding.response.entity.Response;
import com.onboarding.survey.entity.Question;
import com.onboarding.survey.entity.Survey;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
public record ResponseObject(
    String email,
    List<AnswerObject> answerObjects
) {

  public Response of(Survey survey) {
    return Response.builder()
        .email(email)
        .survey(survey)
        .build();
  }

}
