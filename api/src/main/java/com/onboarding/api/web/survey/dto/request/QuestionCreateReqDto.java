package com.onboarding.api.web.survey.dto.request;

import com.onboarding.survey.enums.QuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionCreateReqDto{
  private String title;
  private String description;
  private QuestionType type;
  private boolean required;
  private boolean deleted;
  private List<String> choices;
}
