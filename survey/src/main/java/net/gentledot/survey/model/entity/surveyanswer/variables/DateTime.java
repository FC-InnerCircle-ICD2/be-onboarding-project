package net.gentledot.survey.model.entity.surveyanswer.variables;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DateTime {
    private String format; // "DATE" 또는 "TIME" 또는 "DATE_TIME"
    private LocalDateTime dateTimeValue;

    private DateTime(String format, LocalDateTime dateTimeValue) {
        this.format = format;
        this.dateTimeValue = dateTimeValue;
    }

    public static DateTime of(String format, LocalDateTime dateTime) {
        return new DateTime(format, dateTime);
    }
}
