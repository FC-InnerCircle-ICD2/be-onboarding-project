package net.gentledot.survey.repository;

import net.gentledot.survey.model.entity.surveyanswer.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
    @Query("SELECT sa FROM SurveyAnswer sa WHERE sa.survey.id = :surveyId")
    List<SurveyAnswer> findAllBySurveyId(@Param("surveyId") String surveyId);
}
