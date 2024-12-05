package ziwookim.be_onboarding_project.research.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ziwookim.be_onboarding_project.research.dto.request.AddResearchRequestVo;
import ziwookim.be_onboarding_project.research.dto.request.EditResearchRequestVo;
import ziwookim.be_onboarding_project.research.dto.request.SubmitResearchRequestVo;
import ziwookim.be_onboarding_project.research.dto.response.ResearchAnswerListResponse;
import ziwookim.be_onboarding_project.research.dto.response.ResearchAnswerResponse;
import ziwookim.be_onboarding_project.research.dto.response.ResearchResponse;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerListVo;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerVo;
import ziwookim.be_onboarding_project.research.model.ResearchVo;
import ziwookim.be_onboarding_project.research.service.ResearchService;

import java.util.List;

@Tag(name = "Research", description = "Research API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/research")
public class ResearchController {

    private final ResearchService researchService;


    // TODO: 11/27/24 설문조사 생성 API
    @PostMapping("/add")
    @Operation(summary = "설문조사 생성 API", description = "설문조사 생성 API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResearchResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청. 요청 데이터가 올바르지 않습니다."
            )
    })
    public ResponseEntity<ResearchResponse> addResearch(
            @RequestBody AddResearchRequestVo requestVo) {

        ResearchVo researchVo = researchService.addResearch(requestVo);
        return ResponseEntity.ok(ResearchResponse.of(researchVo));
    }

    // TODO: 11/27/24 설문조사 수정 API
    @PostMapping("/edit")
//    @Operation(summary = "설문조사 수정 API", description = "설문조사 수정 API")
    public ResponseEntity<ResearchResponse> editResearch(
            @RequestBody EditResearchRequestVo requestVo) {

        ResearchVo researchVo = researchService.editResearch(requestVo);
        return ResponseEntity.ok(ResearchResponse.of(researchVo));
    }

    // TODO: 11/27/24 설문조사 응답 제출 API
    @PostMapping("/submit")
//    @Operation(summary = "설문조사 응답 제출 API", description = "설문조사 응답 제출 API")
    public ResponseEntity<ResearchAnswerResponse> submitResearch(
            @RequestBody SubmitResearchRequestVo requestVo) throws JsonProcessingException {

        ResearchAnswerVo researchAnswerVo = researchService.submitResearchAnswer(requestVo);
        return ResponseEntity.ok(ResearchAnswerResponse.of(researchAnswerVo));
    }

    // TODO: 11/27/24 설문조사 응답 조회 API
    @GetMapping("/get/research-answer")
//    @Operation(summary = "설문조사 응답 조회 API", description = "설문조사 응답 조회 API")
    public ResponseEntity<ResearchAnswerResponse> getResearchAnswer(
            @RequestParam Long researchAnswerId) throws JsonProcessingException {

        ResearchAnswerVo researchAnswerVo = researchService.getResearchAnswer(researchAnswerId);
        return ResponseEntity.ok(ResearchAnswerResponse.of(researchAnswerVo));
    }

    @GetMapping("/search/research-answer")
//    @Operation(summary = "설문조사 항목 답변 검색 API", description = "설문조사 항목 답변 검색 API")
    public ResponseEntity<ResearchAnswerListResponse> getSearchResearchAnswer(
            @RequestParam String keyword) throws JsonProcessingException {

        List<ResearchAnswerVo> researchAnswerVoList = researchService.searchResearchAnswer(keyword);
        return ResponseEntity.ok(ResearchAnswerListResponse.of(ResearchAnswerListVo.of(researchAnswerVoList)));
    }
}
