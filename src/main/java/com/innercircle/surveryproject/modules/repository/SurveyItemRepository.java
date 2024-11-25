package com.innercircle.surveryproject.modules.repository;

import com.innercircle.surveryproject.modules.entity.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, Long> {

}
