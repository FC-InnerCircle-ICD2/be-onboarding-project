package net.gentledot.survey.application.service.in.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "서베이 응답 조회 응답")
public record SearchSurveyAnswerResponse(
        @Schema(description = "서베이 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        String surveyId,

        @Schema(description = "응답 리스트", example = """
                    [
                        {
                            "answerId": 1,
                            "answers": [
                                {
                                    "questionName": "이름을 알려주세요",
                                    "answerValue": "홍길동"
                                }
                            ]
                        },
                        {
                            "answerId": 2,
                            "answers": [
                                {
                                    "questionName": "오늘의 기분을 알려주세요",
                                    "answerValue": "좋아요"
                                }
                            ]
                        }
                    ]
                """)
        List<SurveyAnswerValue> answerList
) {
}
