package org.survey.db.selectlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.survey.db.BaseStatus;

import java.util.List;
import java.util.Optional;

public interface SelectListRepository extends JpaRepository<SelectListEntity, Long> {

    Optional<SelectListEntity> findFirstByIdAndSurveyIdAndItemIdAndStatusOrderByIdDesc(
            Long id,
            Long surveyId,
            Long itemId,
            BaseStatus status
    );
    List<SelectListEntity> findAllBySurveyIdAndItemIdAndStatusOrderByIdAsc(
            Long surveyId,
            Long itemId,
            BaseStatus status
    );
}
