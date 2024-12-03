package com.ic2.obd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ic2.obd.domain.Response;
import com.ic2.obd.domain.Survey;

public interface ResponseRepository extends JpaRepository<Response, Long> {
	List<Response> findBySurvey(Survey survey);
	
	@Query("SELECT r FROM Response r " +
		       "JOIN r.answers a " +
		       "JOIN a.question q " +
		       "WHERE r.survey.id = :surveyId " + 
		       "AND q.questionName = :questionName " +
		       "AND a.answer = :answer")
		List<Response> findBySurveyAndQuestionNameAndAnswer(
		    @Param("surveyId") Long surveyId, // ID로 매핑
		    @Param("questionName") String questionName,
		    @Param("answer") String answer
		);
}
