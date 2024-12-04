package net.gentledot.survey.model.entity.surveyanswer.variables;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Selection {
    private String selectedOption;

    private Selection(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public static Selection from(String selectedOption) {
        return new Selection(selectedOption);
    }
}
