package net.gentledot.survey.domain.surveyanswer.variables;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class TextInput {
    private int maxLength;
    private String text;

    private TextInput(int maxLength, String text) {
        this.maxLength = maxLength;
        this.text = text;
    }

    public static TextInput newText(String text) {
        return new TextInput(300, text);
    }
}
