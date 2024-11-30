package org.innercircle.surveyapiapplication.domain.answer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.answer.domain.Answer;

@Entity
@Table(name = "answers")
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "response", nullable = false)
    private String response;

    public static AnswerEntity from(Answer answer) {
        return new AnswerEntity(answer.getId(), answer.getQuestionId(), answer.getResponse());
    }

    public Answer toDomain() {
        return new Answer(this.id, this.questionId, this.response);
    }

}
