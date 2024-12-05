package ic2.onboarding.survey.global;

public enum InputType {

    SHORT_ANSWER,
    LONG_ANSWER,
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    ;

    public boolean isChoiceType() {

        return this == SINGLE_CHOICE || this == MULTIPLE_CHOICE;
    }

}
