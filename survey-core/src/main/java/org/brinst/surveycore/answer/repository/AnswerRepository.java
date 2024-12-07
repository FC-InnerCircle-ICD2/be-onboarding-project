package org.brinst.surveycore.answer.repository;

import java.util.List;

import org.brinst.surveycore.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	@Query("select a from Answer a where a.survey.id = ?1")
	List<Answer> findBySurvey_Id(Long id);

	@Query("SELECT DISTINCT a FROM Answer a " +
		"JOIN FETCH a.answers ap " +
		"JOIN FETCH ap.surveyQuestion sq " +
		"WHERE a.survey.id = :surveyId " +
		"AND (:questionName IS NULL OR LOWER(sq.name) = LOWER(:questionName)) " +
		"AND (:answerValue IS NULL OR " +
		"    (TYPE(ap) = LongAnswer AND TREAT(ap AS LongAnswer).answerValue = :answerValue) OR " +
		"    (TYPE(ap) = ShortAnswer AND TREAT(ap AS ShortAnswer).answerValue = :answerValue) OR " +
		"    (TYPE(ap) = SingleChoiceAnswer AND TREAT(ap AS SingleChoiceAnswer).answerValue = :answerValue) OR " +
		"    (TYPE(ap) = MultiChoiceAnswer AND :answerValue MEMBER OF TREAT(ap AS MultiChoiceAnswer).answerValue))")
	List<Answer> searchAnswers(
		@Param("surveyId") Long surveyId,
		@Param("questionName") String questionName,
		@Param("answerValue") String answerValue
	);
}
