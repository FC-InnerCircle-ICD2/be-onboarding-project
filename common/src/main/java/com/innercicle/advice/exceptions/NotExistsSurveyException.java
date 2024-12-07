package com.innercicle.advice.exceptions;

public class NotExistsSurveyException extends RuntimeException {

    public NotExistsSurveyException() {
        super("존재하지않는 설문입니다.");
    }

}
