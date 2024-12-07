package com.survey.www.surveys.dto.request;

import com.survey.www.surveys.code.SurveyQuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SurveyUpdateRequest {
    @Schema(title = "이름", description = "설문조사 이름입니다")
    private String surveyNm;

    @Schema(title = "설명", description = "설문조사 설명입니다")
    private String description;

    @Schema(title = "삭제 여부", description = "삭제 여부입니다")
    private Boolean isDeleted;

    @Schema(title = "항목", description = "설문 받을 항목입니다")
    private List<SurveyQuestionCommand> surveyQuestions;

    @Builder
    @Getter
    public static class SurveyQuestionCommand {
        @Schema(title = "고유번호", description = "항목 고유번호입니다")
        private Long id;

        @Schema(title = "항목명", description = "항목 이름입니다")
        private String questionNm;

        @Schema(title = "설명", description = "항목 설명입니다")
        private String description;

        @Schema(title = "입력 형태", description = "항목 입력 형태입니다")
        private SurveyQuestionType surveyQuestionType;

        @Schema(title = "필수 여부", description = "항목 필수 여부입니다")
        private Boolean isRequired;

        @Schema(title = "삭제 여부", description = "삭제 여부입니다")
        private Boolean isDeleted;

        @Schema(title = "항목 리스트", description = "설문 받을 항목의 리스트입니다")
        private List<SurveyQuestionOptionCommand> surveyQuestionOptions;
    }

    @Builder
    @Getter
    public static class SurveyQuestionOptionCommand {
        @Schema(title = "고유번호", description = "리스트 고유번호입니다")
        private Long id;

        @Schema(title = "내용", description = "리스트 내용입니다")
        private String content;

        @Schema(title = "삭제 여부", description = "삭제 여부입니다")
        private Boolean isDeleted;
    }
}
