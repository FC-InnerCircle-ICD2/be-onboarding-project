package net.gentledot.survey.model.entity.surveyanswer.variables;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.gentledot.survey.model.enums.AnswerType;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DateTime {
    private AnswerType answerType;
    private String format; // "DATE" 또는 "TIME" 또는 "DATE_TIME"
    private LocalDateTime dateTimeValue;

    private DateTime(AnswerType answerType, String format, LocalDateTime dateTimeValue) {
        this.answerType = answerType;
        this.format = format;
        this.dateTimeValue = dateTimeValue;
    }

    public static DateTime of(AnswerType answerType, String format, LocalDateTime dateTime) {
        return new DateTime(answerType, format, dateTime);
    }
}
