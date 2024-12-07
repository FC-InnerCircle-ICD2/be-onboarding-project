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
@Schema(description = "설문조사 등록 request")
public class AddResearchRequestVo {

    @NotBlank(message="설문조사 제목")
    @Schema(description = "Research name", example = "research_title")
    private String title;

    @NotBlank(message="설문조사 설명")
    @Schema(description = "Research description", example = "research_description")
    private String description;

    @Schema(description = "설문조사 항목", implementation = ResearchItemRequestVo.class)
    private List<ResearchItemRequestVo> itemVoList;
}
