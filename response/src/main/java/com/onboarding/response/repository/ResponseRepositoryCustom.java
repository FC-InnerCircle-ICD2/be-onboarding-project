package com.onboarding.response.repository;

import com.onboarding.response.entity.Response;
import com.onboarding.survey.entity.Survey;
import java.util.List;

public interface ResponseRepositoryCustom {
  List<Response> findResponsesByAdvancedFilters(Survey survey, String questionTitle, String responseValue);
}
