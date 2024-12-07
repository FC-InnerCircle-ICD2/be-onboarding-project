package net.gentledot.survey.domain.surveyanswer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.domain.enums.AnswerType;
import net.gentledot.survey.domain.exception.ServiceError;
import net.gentledot.survey.domain.exception.SurveySubmitValidationException;
import net.gentledot.survey.domain.surveyanswer.variables.Attachment;
import net.gentledot.survey.domain.surveyanswer.variables.DateTime;
import net.gentledot.survey.domain.surveyanswer.variables.Selection;
import net.gentledot.survey.domain.surveyanswer.variables.TextInput;
import net.gentledot.survey.domain.surveybase.SurveyQuestionOption;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Embeddable
public class SurveyQuestionAnswerSnapshot {

    @Embedded
    private TextInput textInputAnswer;

    @Embedded
    private Selection selectionAnswer;

    @Embedded
    private Attachment attachmentAnswer;

    private DateTime dateTimeAnswer;

    private SurveyQuestionAnswerSnapshot(Selection selectionAnswer) {
        this.selectionAnswer = selectionAnswer;
    }

    private SurveyQuestionAnswerSnapshot(TextInput textInput) {
        this.textInputAnswer = textInput;
    }

    private static SurveyQuestionAnswerSnapshot newTextInput(String text) {
        return new SurveyQuestionAnswerSnapshot(
                TextInput.newText(text)
        );
    }

    private static SurveyQuestionAnswerSnapshot newSelection(String selectedOption, List<SurveyQuestionOption> options) {
        return new SurveyQuestionAnswerSnapshot(
                Selection.of(selectedOption, options)
        );
    }

    public static SurveyQuestionAnswerSnapshot of(AnswerType answerType, String answer) {
        return of(answerType, null, answer);
    }

    public static SurveyQuestionAnswerSnapshot of(AnswerType answerType, List<SurveyQuestionOption> options, String answer) {
        if (AnswerType.TEXT.equals(answerType)) {
            return newTextInput(answer);
        } else if (AnswerType.SELECTION.equals(answerType)) {
            return newSelection(answer, options);
        }

        throw new SurveySubmitValidationException(ServiceError.SUBMIT_UNSUPPORTED_ATTRIBUTE);
    }

    public String getAnswer(AnswerType answerType) {
        if (AnswerType.TEXT.equals(answerType)) {
            return textInputAnswer.getText();
        } else if (AnswerType.SELECTION.equals(answerType)) {
            return selectionAnswer.getSelectedOption();
        }

        throw new SurveySubmitValidationException(ServiceError.SUBMIT_UNSUPPORTED_ATTRIBUTE);
    }
}