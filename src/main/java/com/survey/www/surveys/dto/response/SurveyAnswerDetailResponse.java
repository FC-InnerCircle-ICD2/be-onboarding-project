package com.survey.www.surveys.dto.response;

import com.survey.www.surveys.code.SurveyQuestionType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SurveyAnswerDetailResponse {
    private SurveyQuestionType surveyQuestionType;
    private String questionNm;
    private List<AnswerCommand> answers;

    @Getter
    @Builder
    public static class AnswerCommand {
        private String content;
        private String optionNm;
        private int count;
        private double percent;

        public void updatePercent(int totalCnt) {
            if (totalCnt > 0) {
                percent = Math.round((count * 100.0) / totalCnt * 100.0) / 100.0;
            } else {
                percent = 0.0;
            }
        }
    }
}
