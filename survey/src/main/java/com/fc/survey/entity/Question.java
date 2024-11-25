package com.fc.survey.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor
public class Question extends BaseTimeEntity{

  @Comment("설문 항목 테이블 식별자")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("항목 이름")
  @Column
  private String name;

  @Comment("항목 설명")
  @Column
  private String description;

  @Comment("항목 입력 형태")
  @Column
  private QuestionType type;

  @Comment("항목 선택 값 | 로 구분")
  @Column
  private String selectValue;

  @Comment("항목 필수 여부")
  @Column
  private boolean isRequired;

  @Comment("항목 삭제 여부")
  @Column
  private boolean isDelete;

  @ManyToOne
  @JoinColumn(name = "survey_id")
  private Survey survey;

}
