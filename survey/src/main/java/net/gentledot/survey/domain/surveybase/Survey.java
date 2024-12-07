package net.gentledot.survey.domain.surveybase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.application.service.in.model.request.SurveyQuestionRequest;
import net.gentledot.survey.domain.common.BaseEntity;
import net.gentledot.survey.domain.enums.UpdateType;
import net.gentledot.survey.domain.exception.ServiceError;
import net.gentledot.survey.domain.exception.SurveyNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "questions")
@Entity
public class Survey extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "survey")
    private List<SurveyQuestion> questions;

    public static Survey of(String name, String description, List<SurveyQuestion> surveyQuestions) {
        String surveyId = UUID.randomUUID().toString();
        Survey survey = new Survey(surveyId, name, description, surveyQuestions);
        surveyQuestions.forEach(surveyQuestion -> surveyQuestion.setSurvey(survey));
        return survey;
    }

    public void updateSurvey(String name, String description, List<SurveyQuestionRequest> questionRequests) {
        this.name = name;
        this.description = description;
        updateQuestions(questionRequests);
    }

    public void updateQuestions(List<SurveyQuestionRequest> updatedQuestions) {
        for (SurveyQuestionRequest questionRequest : updatedQuestions) {
            UpdateType updateType = questionRequest.getUpdateType();
            if (updateType == null) {
                updateType = UpdateType.ADD;
            }
            switch (updateType) {
                case MODIFY:
                    SurveyQuestion existingQuestion = this.getQuestions().stream()
                            .filter(question -> question.getId().equals(questionRequest.getQuestionId()))
                            .findFirst()
                            .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_QUESTION_NOT_FOUND));
                    existingQuestion.updateFromRequest(questionRequest);
                    break;
                case DELETE:
                    SurveyQuestion questionToRemove = this.getQuestions().stream()
                            .filter(q -> q.getId().equals(questionRequest.getQuestionId()))
                            .findFirst()
                            .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_QUESTION_NOT_FOUND));
                    this.removeQuestion(questionToRemove);
                    break;
                default:
                    SurveyQuestion newQuestion = SurveyQuestion.from(questionRequest);
                    this.addQuestion(newQuestion);
                    break;
            }
        }
    }

    public void addQuestion(SurveyQuestion question) {
        this.questions.add(question);
        question.setSurvey(this);
    }

    public void removeQuestion(SurveyQuestion question) {
        this.questions.remove(question);
        question.setSurvey(null);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return Objects.equals(id, survey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

