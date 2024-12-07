package net.gentledot.survey.domain.surveyanswer;

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
import net.gentledot.survey.domain.enums.ItemRequired;
import net.gentledot.survey.domain.enums.SurveyItemType;
import net.gentledot.survey.domain.exception.ServiceError;
import net.gentledot.survey.domain.exception.SurveySubmitValidationException;
import net.gentledot.survey.domain.surveyanswer.dto.SubmitSurveyAnswerDto;
import net.gentledot.survey.domain.surveybase.Survey;
import net.gentledot.survey.domain.surveybase.SurveyQuestion;
import net.gentledot.survey.domain.surveybase.SurveyQuestionOption;

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

    public static SurveyAnswer of(Survey survey, List<SubmitSurveyAnswerDto> submitSurveyAnswers) {
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

                    SurveyQuestionAnswerSnapshot collectedOptionSnapshot;

                    if (question.getItemType() == SurveyItemType.SINGLE_SELECT ||
                        question.getItemType() == SurveyItemType.MULTI_SELECT) {
                        if (ItemRequired.REQUIRED.equals(question.getRequired()) &&
                            (questionOptions.size() < submitAnswers.size())) {
                            throw new SurveySubmitValidationException(ServiceError.SUBMIT_INVALID_QUESTION_OPTION_ID);
                        }

                        String collectedAnswer = questionOptions.stream()
                                .filter(option -> submitAnswers.contains(option.getOptionText()))
                                .map(SurveyQuestionOption::getOptionText)
                                .collect(Collectors.joining(", "));

                        collectedOptionSnapshot = SurveyQuestionAnswerSnapshot.of(questionSnapshot.getAnswerType(), questionOptions, collectedAnswer);

                    } else {
                        String answer = submitAnswers.isEmpty() ? null : submitAnswers.getFirst();
                        collectedOptionSnapshot = SurveyQuestionAnswerSnapshot.of(questionSnapshot.getAnswerType(), questionOptions, answer);
                    }
                    return SurveyAnswerSubmission.of(null, questionSnapshot, collectedOptionSnapshot);
                })
                .collect(Collectors.toList());

        SurveyAnswer surveyAnswer = new SurveyAnswer(null, survey, answerSubmissions);
        answerSubmissions.forEach(submission -> submission.setSurveyAnswer(surveyAnswer));
        return surveyAnswer;
    }
}
