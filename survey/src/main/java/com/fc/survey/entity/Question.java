package com.fc.survey.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
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
  @Enumerated(EnumType.STRING)
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
  @JsonIgnore
  private Survey survey;

  @Builder
  public Question(Long id, String name, String description, QuestionType type, String selectValue, boolean isRequired, boolean isDelete, Survey survey) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.selectValue = selectValue;
    this.isRequired = isRequired;
    this.isDelete = isDelete;
    this.survey = survey;
  }

}
