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

        Map<Long, SurveyQuestion> existingQuestionsMap = this.getQuestions().stream()
                .collect(Collectors.toMap(SurveyQuestion::getId, question -> question));

        List<SurveyQuestion> updatedQuestions = questionRequests.stream()
                .map(questionRequest -> {
                    SurveyQuestion existingQuestion = existingQuestionsMap.get(questionRequest.getQuestionId());
                    if (existingQuestion != null) {
                        existingQuestion.updateFromRequest(questionRequest);
                        return existingQuestion;
                    } else {
                        return SurveyQuestion.from(questionRequest);
                    }
                })
                .collect(Collectors.toList());

        List<SurveyQuestion> questionsToRemove = this.getQuestions().stream()
                .filter(existingQuestion -> updatedQuestions.stream()
                        .filter(updatedQuestion -> updatedQuestion.getId() != null)
                        .noneMatch(updatedQuestion -> updatedQuestion.getId().equals(existingQuestion.getId())))
                .collect(Collectors.toList());
        this.getQuestions().removeAll(questionsToRemove);
        this.updateQuestions(updatedQuestions);

    }

    public void updateQuestions(List<SurveyQuestion> updatedQuestions) {
        this.questions.clear();
        this.questions.addAll(updatedQuestions);
        updatedQuestions.forEach(question -> question.setSurvey(this));
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

