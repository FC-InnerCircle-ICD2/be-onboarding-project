package com.ic2.obd.dto;

import java.util.List;

public class ResponseDto {
    private List<ResponseAnswerDto> answers;

    // Getters and Setters
    public List<ResponseAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ResponseAnswerDto> answers) {
        this.answers = answers;
    }
}
