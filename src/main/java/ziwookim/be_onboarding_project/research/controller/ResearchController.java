package ziwookim.be_onboarding_project.research.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ziwookim.be_onboarding_project.research.dto.request.*;
import ziwookim.be_onboarding_project.research.dto.response.ResearchAnswerResponse;
import ziwookim.be_onboarding_project.research.dto.response.ResearchAnswerSubmitResponse;
import ziwookim.be_onboarding_project.research.dto.response.ResearchResponse;
import ziwookim.be_onboarding_project.research.model.ResearchAnswerResearchVo;
import ziwookim.be_onboarding_project.research.model.ResearchVo;
import ziwookim.be_onboarding_project.research.service.ResearchService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/research")
public class ResearchController {

    private final ResearchService researchService;

    // TODO: 11/27/24 설문조사 생성 API
    @PostMapping("/add")
    public ResponseEntity<ResearchResponse> addResearch(
            @RequestBody AddResearchRequestVo requestVo) {

        ResearchVo researchVo = researchService.addResearch(requestVo);
        return ResponseEntity.ok(ResearchResponse.of(researchVo));
    }

    // TODO: 11/27/24 설문조사 수정 API
    @PostMapping("/edit")
    public ResponseEntity<ResearchResponse> editResearch(
            @RequestBody EditResearchRequestVo requestVo) {

        ResearchVo researchVo = researchService.editResearch(requestVo);
        return ResponseEntity.ok(ResearchResponse.of(researchVo));
    }

    // TODO: 11/27/24 설문조사 응답 제출 API
    @PostMapping("/submit")
    public ResponseEntity<ResearchAnswerSubmitResponse> submitResearch(
            @RequestBody SubmitResearchRequestVo requestVo) throws JsonProcessingException {

        ResearchAnswerResearchVo researchAnswerResearchVo = researchService.submitResearchAnswer(requestVo);
        return null;
    }

    // TODO: 11/27/24 설문조사 응답 조회 API
    @GetMapping("/get/research-answer")
    public ResponseEntity<ResearchAnswerResponse> getResearchAnswer(
            @RequestParam Long researchAnswerId) {
        return null;
    }

    @GetMapping("/search/research-answer")
    public ResponseEntity<List<ResearchAnswerResponse>> getSearchResearchAnswer(
            @RequestParam String keyword) {
        return null;
    }
}
