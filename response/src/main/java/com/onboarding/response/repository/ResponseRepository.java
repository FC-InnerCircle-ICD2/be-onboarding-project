package com.onboarding.response.repository;

import com.onboarding.response.entity.Response;
import com.onboarding.survey.entity.Survey;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface  ResponseRepository extends JpaRepository<Response, Long> , ResponseRepositoryCustom{

  List<Response> findBySurvey(Survey survey);

  // Advanced 응답 조회
  @Query("SELECT r FROM Response r JOIN r.answers a WHERE r.survey = :survey AND a.questionSnapshot.title = :questionTitle AND a.responseValue = :responseValue")
  List<Response> findBySurveyAndAnswersContaining(@Param("survey") Survey survey,
      @Param("questionTitle") String questionTitle,
      @Param("responseValue") String responseValue);

  boolean existsBySurveyIdAndEmail(Long surveyId, String email);

  Collection<Response> findBySurveyIdAndEmail(Long surveyId, String email);
}
