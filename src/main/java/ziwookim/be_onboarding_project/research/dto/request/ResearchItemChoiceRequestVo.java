package ziwookim.be_onboarding_project.research.dto.request;

import lombok.*;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchItemChoiceRequestVo {
    private String content;
}
