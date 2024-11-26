package ziwookim.be_onboarding_project.dto.request;

import lombok.*;
import ziwookim.be_onboarding_project.research.enums.ResearchItemType;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchRequestVo {

    private String name;
    private String description;
    private ResearchItemType itemType;
    private List<ResearchRequestItemChoiceVo> itemChoiceList;
    private Boolean isRequired;
}
