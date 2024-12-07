package ziwookim.be_onboarding_project.research.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "설문조사 등록 항목 request")
public class ResearchItemRequestVo {

    @NotBlank(message="설문조사 항목 이름")
    @Schema(description = "ResearchItem name", example = "item_name")
    private String name;

    @NotBlank(message="설문조사 항목 설명")
    @Schema(description = "ResearchItem description", example = "item_description")
    private String description;

    @NotBlank(message="설문조사 항목 설명")
    @Schema(description = "ResearchItem itemType... 1) 단답형, 2) 장문형, 3) 단일 선택형, 4) 다중 선택형 ", example = "1")
    private Integer itemType;

    @Schema(description ="설문조사 항목 선택", implementation = ResearchItemChoiceRequestVo.class)
    private List<ResearchItemChoiceRequestVo> itemChoiceList;

    @NotBlank(message="설문조사 항목 필수 입력 여부")
    @Schema(description = "", example = "true")
    private Boolean isRequired;
}
