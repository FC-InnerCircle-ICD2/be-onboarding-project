package com.onboarding.response.service;

import com.onboarding.response.entity.Answer;
import com.onboarding.response.entity.Response;
import com.onboarding.response.repository.ResponseRepository;
import com.onboarding.survey.entity.Survey;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseService {

  private final ResponseRepository responseRepository;

  public Response saveResponse(Survey survey, String email, List<Answer> answers) {
    Response response = Response.builder()
        .survey(survey)
        .email(email)
        .answers(answers)
        .build();

    return responseRepository.save(response);
  }

  public List<Response> findResponsesBySurvey(Survey survey) {
    return responseRepository.findBySurvey(survey);
  }


  public List<Response> searchResponses(Survey survey, String questionTitle, String responseValue) {
    return responseRepository.findBySurveyAndAnswersContaining(survey, questionTitle, responseValue);
  }
}
