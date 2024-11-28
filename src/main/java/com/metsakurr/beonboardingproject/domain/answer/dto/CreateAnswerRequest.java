package com.metsakurr.beonboardingproject.domain.answer.dto;

import com.metsakurr.beonboardingproject.domain.answer.entity.Answer;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateAnswerRequest {
    @NotBlank(message = "idx[응답할 설문 조사 식별자]가 필요합니다.")
    private long idx;

    private List<AnswerRequest> answers;

    @Getter
    public static class AnswerRequest {
        @NotBlank(message = "idx[항목 식별자]가 필요합니다.")
        private long idx;

        private String answer;
    }

}
