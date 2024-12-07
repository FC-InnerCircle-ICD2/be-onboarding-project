package com.innercicle.advice.exceptions;

public class NotExistsReplySurveyException extends RuntimeException {

    public NotExistsReplySurveyException() {
        super("존재하지 않는 설문 응답입니다.");
    }

}
