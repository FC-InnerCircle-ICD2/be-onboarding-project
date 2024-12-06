package net.gentledot.survey.service.in.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Schema(description = "서베이 수정 요청")
@Builder
@ToString
@Getter
@AllArgsConstructor
public class SurveyUpdateRequest implements SurveyRequest {
    @Schema(description = "서베이 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull(message = "업데이트 요청 시 ID는 필수 입력입니다.")
    private String id;

    @Schema(description = "서베이 제목", example = "Updated Survey")
    private String name;

    @Schema(description = "서베이 설명", example = "Updated Description")
    private String description;

    @Schema(description = "질문 리스트", example = """
                [
                    {
                        "questionId": 1,
                        "updateType": "MODIFY",
                        "question": "Updated Question 1",
                        "description": "Updated Description 1",
                        "type": "SINGLE_SELECT",
                        "required": "REQUIRED",
                        "options": [
                            {"option": "Updated Option 1"},
                            {"option": "Updated Option 2"}
                        ]
                    },
                    {
                        "questionId": 2,
                        "updateType": "DELETE"
                    }
                ]
            """)
    @Size(min = 1, max = 10, message = "서베이 항목은 1개에서 최대 10개까지 포함할 수 있습니다.")
    private List<SurveyQuestionRequest> questions;
}
