package com.fastcampus.survey.util.constant;

public enum Must {
    OPTIONAL("O"),
    REQUIRED("R");

    private final String mustFlg;

    Must(String mustFlg) {
        this.mustFlg = mustFlg;
    }

    public String getFlg() {
        return mustFlg;
    }

    public static Must fromValue(String value) throws Exception {
        for (Must or : Must.values()) {
            if (or.getFlg().equals(value)) {
                return or;
            }
        }
        throw new IllegalAccessException("Unknown value : " + value);
    }
}
