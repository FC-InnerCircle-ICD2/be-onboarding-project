package com.onboarding.response.entity;

import com.onboarding.survey.entity.Survey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import org.hibernate.annotations.Comment;

@Entity
public class Response {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("응답자 이메일")
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id")
  private Survey survey;

  @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ResponseDetail> responseDetails;
}
