package com.innercircle.query.infra.persistence.dao;

import com.innercircle.common.domain.survey.question.QuestionSnapshot;
import com.innercircle.common.domain.survey.question.QuestionType;
import com.innercircle.query.infra.persistence.jparepository.AnswerJpaRepository;
import com.innercircle.query.infra.persistence.model.survey.response.Answer;
import com.innercircle.query.infra.persistence.model.survey.response.LongTextAnswerContent;
import com.innercircle.query.infra.persistence.model.survey.response.MultipleChoiceAnswerContent;
import com.innercircle.query.infra.persistence.model.survey.response.ShortTextAnswerContent;
import com.innercircle.query.infra.persistence.model.survey.response.SingleChoiceAnswerContent;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerDao {

	private final AnswerJpaRepository jpaRepository;
	private final JdbcTemplate jdbcTemplate;

	public AnswerDao(AnswerJpaRepository jpaRepository, JdbcTemplate jdbcTemplate) {
		this.jpaRepository = jpaRepository;
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Answer> findBySurveyResponseIdIn(List<String> surveyResponseIds) {
		return jpaRepository.findBySurveyResponseIdIn(surveyResponseIds);
	}

	public List<Answer> search(String query) {
		var sql = """
				    SELECT a.*, ac.*
				    FROM answer a
				    LEFT JOIN answer_content ac ON a.content_id = ac.id
				    WHERE a.question_description LIKE ?
				       OR (ac.type = 'SHORT_TEXT' AND ac.text LIKE ?)
				       OR (ac.type = 'LONG_TEXT' AND ac.text LIKE ?)
				       OR (ac.type = 'SINGLE_CHOICE' AND ac.selected_option = ?)
				       OR (ac.type = 'MULTIPLE_CHOICE' AND ac.selected_options LIKE ?)
				""";

		return jdbcTemplate.query(
				sql,
				answerRowMapper(),
				"%" + query + "%",
				"%" + query + "%",
				"%" + query + "%",
				query,
				"%" + query + "%"
		);
	}

	private RowMapper<Answer> answerRowMapper() {
		return (rs, rowNum) -> {

			var questionOptions = rs.getString("question_options") != null
					? List.of(rs.getString("question_options").split(",")) : List.<String>of();
			var questionSnapshot = new QuestionSnapshot(
					rs.getString("question_id"),
					rs.getString("question_survey_id"),
					rs.getString("question_name"),
					rs.getString("question_description"),
					rs.getBoolean("question_required"),
					QuestionType.valueOf(rs.getString("question_type")),
					questionOptions
			);

			var type = rs.getString("type");
			var content = switch (type) {
				case "SHORT_TEXT" -> new ShortTextAnswerContent(rs.getString("text"));
				case "LONG_TEXT" -> new LongTextAnswerContent(rs.getString("text"));
				case "SINGLE_CHOICE" -> new SingleChoiceAnswerContent(rs.getString("selected_option"));
				case "MULTIPLE_CHOICE" -> {
					var multipleChoiceOptions = rs.getString("selected_options") != null
							? List.of(rs.getString("selected_options").split(",")) : List.<String>of();
					yield new MultipleChoiceAnswerContent(multipleChoiceOptions);
				}
				default -> throw new IllegalStateException("Unexpected value: " + type);
			};

			return new Answer(
					rs.getString("id"),
					rs.getString("survey_response_id"),
					questionSnapshot,
					content
			);
		};
	}
}
