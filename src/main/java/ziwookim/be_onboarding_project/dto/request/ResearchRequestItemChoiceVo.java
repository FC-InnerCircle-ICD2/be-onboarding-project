package ziwookim.be_onboarding_project.dto.request;

import lombok.*;
import ziwookim.be_onboarding_project.research.enums.ResearchItemType;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchRequestItemChoiceVo {

    private String content;
}
