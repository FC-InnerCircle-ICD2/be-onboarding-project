package ic2.onboarding.survey.repository;

import ic2.onboarding.survey.entity.AnswerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface AnswerHistoryJpaRepository extends JpaRepository<AnswerHistory, Long> {

    List<AnswerHistory> findAllBySurveyUuidAndQuestionName(String uuid, String questionName);

}
