package com.icd.survey.api.repository.survey;

import com.icd.survey.api.entity.survey.ItemResponseOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface ResponseOptionRepository extends JpaRepository<ItemResponseOption, Long> {
    @Modifying
    @Query("UPDATE ItemResponseOption o SET o.isDeleted = true WHERE o.surveyItem.itemSeq = :surveyItemSeq")
    int makeAllAsDeletedBySurveyItemSeq(@Param("surveyItemSeq") Long surveyItemSeq);
}
