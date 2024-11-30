package org.survey.db.selectlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.survey.db.BaseStatus;

import java.util.List;

public interface SelectListRepository extends JpaRepository<SelectListEntity, Long> {
    List<SelectListEntity> findAllBySurveyIdAndItemIdAndStatusOrderByIdAsc(
            Long surveyId,
            Long itemId,
            BaseStatus status
    );
}
