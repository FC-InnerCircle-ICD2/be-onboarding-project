package ziwookim.be_onboarding_project.research.dto.request;

import lombok.*;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchAnswerVo {
    private Object answer;

    public boolean isEmptyStringAnswerType() {
        return (answer instanceof String && ((String) answer).isEmpty());
    }
}
