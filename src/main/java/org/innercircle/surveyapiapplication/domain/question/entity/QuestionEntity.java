package org.innercircle.surveyapiapplication.domain.question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.question.domain.LongAnswerQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.MultiChoiceQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.domain.ShortAnswerQuestion;
import org.innercircle.surveyapiapplication.domain.question.domain.SingleChoiceQuestion;
import org.innercircle.surveyapiapplication.domain.question.entity.id.QuestionId;
import org.innercircle.surveyapiapplication.global.entity.BaseEntity;
import org.innercircle.surveyapiapplication.global.exception.CustomException;
import org.innercircle.surveyapiapplication.global.exception.CustomResponseStatus;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
@Getter
public abstract class QuestionEntity extends BaseEntity {

    @Id
    private QuestionId questionId;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 1000, nullable = true)
    private String description;

    @Column(nullable = false)
    private boolean required;

    @Column(name = "survey_id", nullable = false)
    private Long surveyId;

    public QuestionEntity(Long questionId, int questionVersion, String name, String description, boolean required, Long surveyId) {
        this.questionId = new QuestionId(questionId, questionVersion);
        this.name = name;
        this.description = description;
        this.required = required;
        this.surveyId = surveyId;
    }

    public static QuestionEntity from(Question question) {
        if (question instanceof ShortAnswerQuestion) {
            return new ShortAnswerQuestionEntity(question.getId(), question.getVersion(), question.getName(), question.getDescription(), question.isRequired(), question.getSurveyId());
        }
        if (question instanceof LongAnswerQuestion) {
            return new LongAnswerQuestionEntity(question.getId(), question.getVersion(), question.getName(), question.getDescription(), question.isRequired(), question.getSurveyId());
        }
        if (question instanceof SingleChoiceQuestion) {
            return new SingleChoiceQuestionEntity(question.getId(), question.getVersion(), question.getName(), question.getDescription(), question.isRequired(), question.getSurveyId(), ((SingleChoiceQuestion) question).getOptions());
        }
        if (question instanceof MultiChoiceQuestion) {
            return new MultiChoiceQuestionEntity(question.getId(), question.getVersion(), question.getName(), question.getDescription(), question.isRequired(), question.getSurveyId(), ((MultiChoiceQuestion) question).getOptions());
        }
        throw new CustomException(CustomResponseStatus.NOT_FOUND_QUESTION_FORMAT);
    }

    public abstract Question toDomain();

}
