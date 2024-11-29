package ziwookim.be_onboarding_project.research.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchItemRequestVo {
    private String name;
    private String description;
    private Integer itemType;
    private List<ResearchItemChoiceRequestVo> itemChoiceList;
    private Boolean isRequired;
}
