package com.innercircle.command.infra.persistence.jparepository;

import com.innercircle.command.domain.survey.response.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerJpaRepository extends JpaRepository<Answer, String> {

}
