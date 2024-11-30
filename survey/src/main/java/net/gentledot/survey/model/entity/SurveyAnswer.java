package net.gentledot.survey.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.dto.request.SubmitSurveyAnswer;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveySubmitValidationException;
import net.gentledot.survey.model.enums.SurveyItemType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "survey")
@Entity
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "surveyAnswer")
    private List<SurveyAnswerSubmission> answers;

    public static SurveyAnswer of(Survey survey, List<SubmitSurveyAnswer> answers) {
        // SubmitSurveyAnswer -> SurveyAnswerSubmission
        List<SurveyAnswerSubmission> answerSubmissions = answers.stream()
                .map(answer -> {
                    SurveyQuestion question = survey.getQuestions().stream()
                            .filter(surveyQuestion -> surveyQuestion.getId().equals(answer.getQuestionId()))
                            .findFirst()
                            .orElseThrow(() -> new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_ID));

                    Optional<SurveyQuestionOption> questionOptionOptional = question.getOptions().stream()
                            .filter(option -> option.getId().equals(answer.getQuestionOptionId()))
                            .findFirst();

                    if ((question.getItemType().equals(SurveyItemType.SINGLE_SELECT) ||
                         question.getItemType().equals(SurveyItemType.MULTI_SELECT))
                        && questionOptionOptional.isEmpty()) {
                        throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_OPTION_ID);
                    }

                    return SurveyAnswerSubmission.of(question, questionOptionOptional.orElse(null), answer.getAnswer());
                })
                .collect(Collectors.toList());

        SurveyAnswer surveyResponse = new SurveyAnswer(null, survey, answerSubmissions);
        answerSubmissions.forEach(answer -> answer.setSurveyAnswer(surveyResponse));
        return surveyResponse;
    }
}
