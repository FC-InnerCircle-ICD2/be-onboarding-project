package net.gentledot.survey.service.in.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Schema(description = "설문조사 응답 요청")
@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitSurveyAnswer {
    @Schema(description = "질문 ID", example = "1")
    private Long questionId;

    @Schema(description = "응답 값 리스트", example = "[\"홍길동\"]")
    private List<String> answer;
}
