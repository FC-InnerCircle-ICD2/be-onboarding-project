package ziwookim.be_onboarding_project.research.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerItemVo;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResearchAnswerItemResponse {
    private Long researchItemId;
    private String name;
    private String description;
    private Integer itemType;
    private String  itemTypeName;
    private Boolean isRequired;
    private List<ResearchItemChoiceResponse> researchItemChoiceResponseList;
    private Object answer;

    public static ResearchAnswerItemResponse of(ResearchAnswerItemVo vo) {
        return ResearchAnswerItemResponse.builder()
                .researchItemId(vo.getId())
                .name(vo.getName())
                .description(vo.getDescription())
                .itemType(vo.getItemType())
                .itemTypeName(vo.getItemTypeName())
                .isRequired(vo.getIsRequired())
                .researchItemChoiceResponseList(vo.getItemChoiceList().stream().map(ResearchItemChoiceResponse::of).toList())
                .answer(vo.getAnswer())
                .build();
    }

}
