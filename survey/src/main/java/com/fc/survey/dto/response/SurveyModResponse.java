package com.fc.survey.dto.response;

import com.fc.survey.entity.Question;
import java.util.List;
import lombok.Getter;

@Getter
public class SurveyModResponse {

  private final Long id;
  private final String name;
  private final String description;
  private final List<Question> questions;

  public SurveyModResponse(Long id, String name, String description, List<Question> questions) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.questions = questions;
  }

}
