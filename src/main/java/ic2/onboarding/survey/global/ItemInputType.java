package ic2.onboarding.survey.global;

public enum ItemInputType {

    SHORT_ANSWER,
    LONG_ANSWER,
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    ;

    public static ItemInputType fromString(String input) {

        try {
            return ItemInputType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BizException(ErrorCode.NOT_VALIDATED_INPUT_TYPE);
        }
    }

    public boolean isChoiceType() {

        return this == SINGLE_CHOICE || this == MULTIPLE_CHOICE;
    }

}
