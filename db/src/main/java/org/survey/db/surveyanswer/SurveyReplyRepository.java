package org.survey.db.surveyanswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.survey.db.BaseStatus;

import java.util.List;
import java.util.Optional;

public interface SurveyReplyRepository extends JpaRepository<SurveyReplyEntity, Long> {

    Optional<SurveyReplyEntity> findFirstByIdAndSurveyIdAndItemIdAndStatusOrderByIdDesc(
            Long id,
            Long surveyId,
            Long itemId,
            BaseStatus status
    );

    List<SurveyReplyEntity> findAllBySurveyIdAndContentAndStatusOrderByIdAscItemIdAsc(
            Long surveyId,
            String content,
            BaseStatus status
    );

    List<SurveyReplyEntity> findAllBySurveyIdAndStatusOrderByIdAscItemIdAsc(
            Long surveyId,
            BaseStatus status
    );

    List<SurveyReplyEntity> findAllBySurveyIdAndItemIdAndStatusOrderByIdAsc(
            Long surveyId,
            Long itemId,
            BaseStatus status
    );
}
