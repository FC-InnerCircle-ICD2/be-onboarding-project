package net.gentledot.survey.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Schema(description = "서베이 생성 요청")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyCreateRequest implements SurveyRequest {
    @Schema(description = "서베이 제목", example = "나만의 설문")
    @NotBlank(message = "서베이의 제목을 입력해주세요.")
    private String name;

    @Schema(description = "서베이 설명", example = "간단한 설문입니다.")
    private String description;

    @Schema(description = "질문 리스트", example = """
                [
                    {
                        "question": "이름을 알려주세요",
                        "description": "당신의 이름은 무엇입니까?",
                        "type": "TEXT",
                        "required": "REQUIRED",
                        "options": [
                            {"option" : "입력 1"}
                        ]
                    },
                    {
                        "question": "오늘의 기분을 알려주세요",
                        "description": "가벼운 질문부터",
                        "type": "SINGLE_SELECT",
                        "required": "REQUIRED",
                        "options": [
                            {"option": "좋아요"},
                            {"option": "안좋아요"}
                        ]
                    }
                ]
            """)
    @Size(min = 1, max = 10, message = "서베이 항목은 1개에서 최대 10개까지 포함할 수 있습니다.")
    private List<SurveyQuestionRequest> questions;
}
