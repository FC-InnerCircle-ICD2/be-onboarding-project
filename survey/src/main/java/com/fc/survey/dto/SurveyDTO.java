package com.fc.survey.dto;

import com.fc.survey.entity.Question;
import com.fc.survey.entity.Survey;
import java.util.List;
import lombok.Data;

@Data
public class SurveyDTO {

  private String name;

  private String description;

  private List<QuestionDTO> questions;

  public Survey toEntity() {
    Survey survey = Survey.builder()
        .name(name)
        .description(description)
        .build();
    List<Question> questionEntities = questionDTOToEntity(survey);
    survey.setQuestions(questionEntities);
    return survey;
  }

  private List<Question> questionDTOToEntity(Survey survey) {
    return questions.stream().map(questionDTO -> Question.builder()
            .name(questionDTO.getName())
            .description(questionDTO.getDescription())
            .type(questionDTO.getType())
            .selectValue(questionDTO.getSelectValue())
            .isRequired(questionDTO.isRequired())
            .isDelete(questionDTO.isDelete())
            .survey(survey) // 부모 Survey 설정
            .build())
        .toList();
  }
}
