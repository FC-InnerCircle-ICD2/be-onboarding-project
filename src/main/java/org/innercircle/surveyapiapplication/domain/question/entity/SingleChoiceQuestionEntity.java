package org.innercircle.surveyapiapplication.domain.question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.innercircle.surveyapiapplication.domain.answer.entity.AnswerEntity;
import org.innercircle.surveyapiapplication.domain.question.domain.SingleChoiceQuestion;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("SINGLE_CHOICE")
public class SingleChoiceQuestionEntity extends QuestionEntity {

    @Column(name = "options")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> options;

    @Transient
    private AnswerEntity answerEntity;

    public SingleChoiceQuestionEntity(String name, String description, boolean required, Long surveyId, List<String> options, AnswerEntity answerEntity) {
        super(name, description, required, surveyId);
        this.options = options;
        this.answerEntity = answerEntity;
    }

    @Override
    public SingleChoiceQuestion toDomain() {
        return new SingleChoiceQuestion(
            this.getId(),
            this.getName(),
            this.getDescription(),
            this.isRequired(),
            this.getSurveyId(),
            this.getOptions(),
            this.getAnswerEntity().toDomain()
        );
    }

}
