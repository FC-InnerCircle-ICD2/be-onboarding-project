package com.ic2.obd.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 질문(Question) 엔티티.
 * 데이터베이스의 Question 테이블과 매핑
 */
@Entity // 이 클래스가 JPA 엔티티임을 선언
public class Question {
	/**
     * 질문의 고유 ID. Primary Key로 설정
     * @Id: Primary Key로 사용
     * @GeneratedValue: ID 값을 자동으로 생성. IDENTITY 전략 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 질문 제목. NULL 값을 허용하지 않음
     * @Column: 해당 필드를 데이터베이스의 컬럼으로 매핑
     * nullable=false: 이 컬럼은 NULL 값을 허용하지 않음
     */
    @Column(name = "question_name", nullable = false) 
    private String questionName;
    
    private String questionDescription; // 질문 설명 (선택 사항)
    
    @Column(nullable = false) 
    private String inputType;	// 응답 형식(단답형, 장문형 등)
    
    private boolean isRequired; // 필수 입력 여부
    
    @ElementCollection
    private List<String> options = new ArrayList<>(); // 옵션 리스트(단일 선택, 다중 선택)와 같은 간단한 컬렉션 데이터를 별도 테이블에 저장합
    
    /**
     * 질문이 속한 설문조사를 나타낸다.
     * @ManyToOne: N:1 관계를 나타낸다.
     * @JoinColumn: 이 필드가 외래 키로 사용됨을 명시
     * name="survey_id": 데이터베이스에서 외래 키 컬럼 이름을 survey_id로 지정
     */
    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false) // 설문조사와 연결된 외래 키
    @JsonIgnore // 순환 참조 문제를 방지하기 위해 관계의 JSON 변환 시 이 필드는 제외
    private Survey survey;
    
    // Getter와 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
