package com.ic2.obd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
     * 질문의 내용. NULL 값을 허용하지 않음
     * @Column: 해당 필드를 데이터베이스의 컬럼으로 매핑
     * nullable=false: 이 컬럼은 NULL 값을 허용하지 않음
     */
    @Column(nullable = false)
    private String content;
    
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
    
    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
