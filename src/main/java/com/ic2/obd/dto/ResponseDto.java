package com.ic2.obd.dto;

import java.util.List;

public class ResponseDto {
	private Long id; // 응답 ID
    private Long surveyId; // 설문조사 ID
    private List<ResponseAnswerDto> answers; // 응답 데이터 리스트


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
    
    public List<ResponseAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ResponseAnswerDto> answers) {
        this.answers = answers;
    }
}
