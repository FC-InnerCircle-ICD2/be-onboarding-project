package ic2.onboarding.survey.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AnswerInfo {

    // 항목 이름
    private String questionName;

    // 단일 답변
    private String singleTextAnswer;

    // 리스트 답변
    private List<String> multipleTextAnswer;

}
