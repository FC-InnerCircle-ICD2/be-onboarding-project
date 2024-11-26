package ziwookim.be_onboarding_project.research.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ziwookim.be_onboarding_project.dto.request.ResearchAnswerRequestVo;
import ziwookim.be_onboarding_project.dto.request.ResearchRequestVo;
import ziwookim.be_onboarding_project.dto.response.ResearchAnswerResponse;
import ziwookim.be_onboarding_project.dto.response.ResearchAnswerSubmitResponse;
import ziwookim.be_onboarding_project.dto.response.ResearchResponse;
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
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam List<ResearchRequestVo> editableResearchRequestVoList) {

        return null;
    }

    // TODO: 11/27/24 설문조사 수정 API
    @PatchMapping("/edit")
    public ResponseEntity<ResearchResponse> editResearch(
            @RequestParam Long researchId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam List<ResearchRequestVo> editableResearchRequestVoList) {

        return null;
    }

    // TODO: 11/27/24 설문조사 응답 제출 API
    @PostMapping("/submit-answer")
    public ResponseEntity<ResearchAnswerSubmitResponse> submitResearchAnswer(
            @RequestParam Long researchId,
            @RequestParam List<ResearchAnswerRequestVo> submitResearchRequestVoList) {

        return null;
    }

    // TODO: 11/27/24 설문조사 응답 조회 API
    @GetMapping("/get/submit-answer")
    public ResponseEntity<ResearchAnswerResponse> getResearchAnswer(
            @RequestParam Long researchAnswerId) {
        return null;
    }

}
