package net.gentledot.survey.model.entity.surveybase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.dto.request.SurveyQuestionRequest;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveyNotFoundException;
import net.gentledot.survey.model.entity.common.BaseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Map<Long, SurveyQuestion> existingQuestionsMap = this.getQuestions().stream()
                .collect(Collectors.toMap(SurveyQuestion::getId, question -> question));

        for (SurveyQuestionRequest questionRequest : updatedQuestions) {
            switch (questionRequest.getUpdateType()) {
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

