package org.brinst.surveycore.repository;

import java.util.List;

import org.brinst.surveycore.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	@Query("select a from Answer a where a.survey.id = ?1")
	List<Answer> findBySurvey_Id(Long id);
}
