package com.onboarding.servey.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onboarding.servey.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

	@Query(""
		+ "	SELECT a "
		+ "	FROM Answer a "
		+ "	WHERE (:query IS NULL OR :query = '' OR a.questionSnapShot.name LIKE CONCAT('%', :query, '%'))")
	Page<Answer> search(Pageable pageable, @Param("query") String query);
}
