package com.ic2.obd.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * 설문조사(Survey) 엔티티.
 * 데이터베이스의 Survey 테이블과 매핑
 */

@Entity // 이 클래스가 JPA 엔티티임을 선언
public class Survey {
	/**
     * 설문조사의 고유 ID. Primary Key로 설정
     * @Id: 데이터베이스 테이블의 Primary Key임을 지정
     * @GeneratedValue: ID 값을 자동으로 생성. IDENTITY 전략은 데이터베이스가 자동으로 ID 값을 증가
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 설문조사의 제목. NULL 값을 허용하지 않음
     * @Column: 해당 필드를 데이터베이스의 컬럼으로 매핑
     * nullable=false: NULL 값을 허용하지 않음
     */
    @Column(nullable = false)
    private String title;
    
    /**
     * 설문조사에 포함된 질문 목록
     * @OneToMany: Survey와 Question 간의 1:N 관계를 나타냅니다.
     * mappedBy="survey": Question 엔티티의 survey 필드에 의해 매핑됨을 나타냄
     * cascade=CascadeType.ALL: 부모(Survey) 엔티티와 함께 질문도 저장, 수정, 삭제됨
     * orphanRemoval=true: 부모 엔티티에서 제거된 자식 엔티티를 자동으로 삭제
     */
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    // 양방향 관계 설정을 위한 편의 메서드.
    public void addQuestion(Question question) {
        questions.add(question);
        question.setSurvey(this); // Question 엔티티에도 Survey 설정
    }
    
    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setSurvey(null); // 부모와의 관계를 끊음
    }
    
    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
