package com.practice.survey.surveyItemOption.repository;

import com.practice.survey.surveyItem.model.entity.SurveyItem;
import com.practice.survey.surveyItemOption.model.entity.SurveyItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyItemOptionRepository extends JpaRepository<SurveyItemOption, Long> {
//    List<SurveyItemOption> findBySurveyItem(SurveyItem surveyItem);

    @Query("SELECT o.optionText FROM SurveyItemOption o WHERE o.item = :item")
    List<String> findOptionTextBySurveyItem(@Param("item") SurveyItem surveyItem);

}
