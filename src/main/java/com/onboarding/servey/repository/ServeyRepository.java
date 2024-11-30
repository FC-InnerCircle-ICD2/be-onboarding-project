package com.onboarding.servey.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onboarding.servey.domain.Servey;

public interface ServeyRepository extends JpaRepository<Servey, Long> {

	@Query(""
		+ "	SELECT s "
		+ "	FROM Servey s "
		+ "	WHERE s.id IN ("
		+ "		SELECT DISTINCT s1.id "
		+ "		FROM Servey s1 "
		+ "		LEFT JOIN s1.questions q "
		+ "		WHERE (:name IS NULL OR :name = '' OR q.name LIKE CONCAT('%', :name, '%'))"
		+ "		AND (:answer IS NULL OR :answer = '' OR q.answer LIKE CONCAT('%', :answer, '%'))"
		+ "	)")
	Page<Servey> findServeysByNameAndAnswer(Pageable pageable, @Param("name") String name, @Param("answer") String answer);
}
