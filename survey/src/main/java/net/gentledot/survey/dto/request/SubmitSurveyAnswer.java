package net.gentledot.survey.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitSurveyAnswer {
    private Long questionId;
    private List<String> answer;
}
