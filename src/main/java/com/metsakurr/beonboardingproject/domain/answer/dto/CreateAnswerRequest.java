package com.metsakurr.beonboardingproject.domain.answer.dto;

import com.metsakurr.beonboardingproject.domain.answer.entity.Answer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateAnswerRequest {
    @Min(value = 1, message = "유효한 idx[응답할 설문 조사 식별자]가 필요합니다.")
    private int idx;

    private List<AnswerRequest> answers;

    @Getter
    public static class AnswerRequest {
        @Min(value = 1, message = "유효한 idx[항목 식별자]가 필요합니다.")
        private int idx;

        private String answer;
    }

}
