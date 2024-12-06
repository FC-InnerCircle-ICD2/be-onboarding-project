package com.innercircle.surveryproject.modules.repository;

import com.innercircle.surveryproject.modules.entity.SurveyAnswerMapValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyAnswerMapValueRepository extends JpaRepository<SurveyAnswerMapValue, Long> {

    @Query("SELECT v FROM SurveyAnswerMapValue v WHERE v.surveyAnswer.surveyAnswerId.surveyId = :surveyId")
    List<SurveyAnswerMapValue> findBySurveyAnswerId(Long surveyId);

}
