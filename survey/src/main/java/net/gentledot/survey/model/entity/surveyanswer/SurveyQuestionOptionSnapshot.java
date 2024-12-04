package net.gentledot.survey.model.entity.surveyanswer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveySubmitValidationException;
import net.gentledot.survey.model.entity.surveyanswer.variables.Attachment;
import net.gentledot.survey.model.entity.surveyanswer.variables.DateTime;
import net.gentledot.survey.model.entity.surveyanswer.variables.Selection;
import net.gentledot.survey.model.entity.surveyanswer.variables.TextInput;
import net.gentledot.survey.model.entity.surveybase.SurveyQuestionOption;
import net.gentledot.survey.model.enums.AnswerType;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Embeddable
public class SurveyQuestionOptionSnapshot {
    private String optionText;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    @Embedded
    private TextInput textInputAnswer;

    @Embedded
    private Selection selectionAnswer;

    @Embedded
    private Attachment attachmentAnswer;

    private DateTime dateTimeAnswer;

    private SurveyQuestionOptionSnapshot(String optionText, AnswerType answerType, Selection selectionAnswer) {
        this.optionText = optionText;
        this.answerType = answerType;
        this.selectionAnswer = selectionAnswer;
    }

    private SurveyQuestionOptionSnapshot(String optionText, AnswerType answerType, TextInput textInput) {
        this.optionText = optionText;
        this.answerType = answerType;
        this.textInputAnswer = textInput;
    }

    private static SurveyQuestionOptionSnapshot newTextInput(String optionText, String text) {
        return new SurveyQuestionOptionSnapshot(
                optionText, AnswerType.TEXT, TextInput.newText(text)
        );
    }

    private static SurveyQuestionOptionSnapshot newSelection(String optionText, String selectedOption) {
        return new SurveyQuestionOptionSnapshot(
                optionText, AnswerType.SELECTION, Selection.from(selectedOption)
        );
    }

    public static SurveyQuestionOptionSnapshot of(AnswerType answerType, SurveyQuestionOption surveyQuestionOption, String answer) {
        String targetOptionText = surveyQuestionOption.getOptionText();
        if (AnswerType.TEXT.equals(answerType)) {
            return newTextInput(targetOptionText, answer);
        } else if (AnswerType.SELECTION.equals(answerType)) {
            return newSelection(targetOptionText, targetOptionText);
        }

        throw new SurveySubmitValidationException(ServiceError.SUBMIT_UNSUPPORTED_ATTRIBUTE);
    }

    public String getOptionText() {
        return optionText;
    }

    public String getAnswer() {
        if (AnswerType.TEXT.equals(answerType)) {
            return textInputAnswer.getText();
        } else if (AnswerType.SELECTION.equals(answerType)) {
            return selectionAnswer.getSelectedOption();
        }

        throw new SurveySubmitValidationException(ServiceError.SUBMIT_UNSUPPORTED_ATTRIBUTE);
    }
}