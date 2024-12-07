package com.fastcampus.survey.util.constant;

public enum AnsType {
    SHORT("S"),
    LONG("L"),
    LIST_CHOOSE_ONE("LCO"),
    LIST_CHOOSE_MULTI("LCM");

    private final String ansType;

    AnsType(String ansType) {
        this.ansType = ansType;
    }

    public String getAnsType() {
        return ansType;
    }

    public static AnsType fromValue(String value) throws Exception {
        for (AnsType type : AnsType.values()) {
            if (type.ansType.equals(value)) {
                return type;
            }
        }
        throw new IllegalAccessException("Unknown value : " + value);
    }
}
