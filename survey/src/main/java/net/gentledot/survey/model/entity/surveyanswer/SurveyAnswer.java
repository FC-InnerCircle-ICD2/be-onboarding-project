package net.gentledot.survey.model.entity.surveyanswer;

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
import net.gentledot.survey.model.entity.surveybase.Survey;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestion;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestionOption;
import net.gentledot.survey.model.enums.AnswerType;
import net.gentledot.survey.model.enums.ItemRequired;
import net.gentledot.survey.model.enums.SurveyItemType;

import java.util.List;
import java.util.Map;
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

    public static SurveyAnswer of(Survey survey, List<SubmitSurveyAnswer> submitSurveyAnswers) {
        Map<Long, SurveyQuestion> surveyQuestionMap = survey.getQuestions()
                .stream().collect(Collectors.toMap(
                        SurveyQuestion::getId,
                        question -> question));

        if (surveyQuestionMap.size() != submitSurveyAnswers.size()) {
            throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_ID);
        }

        List<SurveyAnswerSubmission> answerSubmissions = submitSurveyAnswers.stream()
                .map(submitSurveyAnswer -> {
                    Long questionId = submitSurveyAnswer.getQuestionId();
                    SurveyQuestion question = surveyQuestionMap.get(questionId);

                    if (question == null) {
                        throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_ID);
                    }

                    List<String> submitAnswers = submitSurveyAnswer.getAnswer();
                    List<SurveyQuestionOption> questionOptions = question.getOptions();
                    SurveyQuestionSnapshot questionSnapshot = SurveyQuestionSnapshot.from(question);

                    if (ItemRequired.REQUIRED.equals(question.getRequired()) &&
                        (questionOptions.size() < submitAnswers.size())) {
                        throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_OPTION_ID);
                    }

                    List<SurveyQuestionOptionSnapshot> collectedOptionSnapshots = null;

                    if (question.getItemType() == SurveyItemType.SINGLE_SELECT ||
                        question.getItemType() == SurveyItemType.MULTI_SELECT) {
                        collectedOptionSnapshots = question.getOptions().stream()
                                .filter(option -> submitAnswers.contains(option.getOptionText()))
                                .map(option -> SurveyQuestionOptionSnapshot.of(AnswerType.SELECTION, option, "true"))
                                .collect(Collectors.toList());
                    } else {
                        String answer = submitAnswers.isEmpty() ? null : submitAnswers.getFirst();
                        collectedOptionSnapshots = question.getOptions().stream()
                                .map(option -> SurveyQuestionOptionSnapshot.of(AnswerType.TEXT, option, answer))
                                .collect(Collectors.toList());
                    }
                    return SurveyAnswerSubmission.of(null, questionSnapshot, collectedOptionSnapshots);
                })
                .collect(Collectors.toList());

        SurveyAnswer surveyAnswer = new SurveyAnswer(null, survey, answerSubmissions);
        answerSubmissions.forEach(submission -> submission.setSurveyAnswer(surveyAnswer));
        return surveyAnswer;
    }
}
