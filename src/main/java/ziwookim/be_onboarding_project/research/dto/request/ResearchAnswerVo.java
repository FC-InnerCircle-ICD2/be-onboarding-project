package ziwookim.be_onboarding_project.research.dto.request;

import lombok.*;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchAnswerVo {
    private Object answer;

    public boolean isValidAnswerType() {
        return switch (answer) {
            case String s -> true;
            case Long l -> true;
            case Long[] longs -> true;
            case null, default -> false;
        };
    }

    public boolean isEmptyStringAnswerType() {
        return (answer instanceof String && ((String) answer).isEmpty());
    }
}
