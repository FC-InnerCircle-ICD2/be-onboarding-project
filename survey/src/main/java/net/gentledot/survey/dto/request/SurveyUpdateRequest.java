package net.gentledot.survey.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Getter
@AllArgsConstructor
public class SurveyUpdateRequest implements SurveyRequest {
    @NotNull(message = "업데이트 요청 시 ID는 필수 입력입니다.")
    private String id;
    private String name;
    private String description;

    @Size(min = 1, max = 10, message = "서베이 항목은 1개에서 최대 10개까지 포함할 수 있습니다.")
    private List<SurveyQuestionRequest> questions;
}
