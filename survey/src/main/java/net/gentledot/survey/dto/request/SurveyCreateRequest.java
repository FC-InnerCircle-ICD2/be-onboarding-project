package net.gentledot.survey.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyCreateRequest implements SurveyRequest {
    @NotBlank(message = "서베이의 제목을 입력해주세요.")
    private String name;
    private String description;

    @Size(min = 1, max = 10, message = "서베이 항목은 1개에서 최대 10개까지 포함할 수 있습니다.")
    private List<SurveyQuestionRequest> questions;
}
