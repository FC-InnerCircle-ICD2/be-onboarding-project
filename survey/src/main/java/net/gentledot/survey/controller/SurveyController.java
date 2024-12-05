package net.gentledot.survey.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.gentledot.survey.dto.common.ServiceResponse;
import net.gentledot.survey.dto.request.SearchSurveyAnswerRequest;
import net.gentledot.survey.dto.request.SubmitSurveyAnswer;
import net.gentledot.survey.dto.request.SurveyCreateRequest;
import net.gentledot.survey.dto.request.SurveyUpdateRequest;
import net.gentledot.survey.dto.response.SearchSurveyAnswerResponse;
import net.gentledot.survey.dto.response.SurveyCreateResponse;
import net.gentledot.survey.dto.response.SurveyUpdateResponse;
import net.gentledot.survey.service.SurveyAnswerService;
import net.gentledot.survey.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Survey", description = "설문조사 서비스 API")
@RequestMapping("v1/survey")
@RestController
public class SurveyController {
    private final SurveyService surveyService;
    private final SurveyAnswerService surveyAnswerService;

    public SurveyController(SurveyService surveyService, SurveyAnswerService surveyAnswerService) {
        this.surveyService = surveyService;
        this.surveyAnswerService = surveyAnswerService;
    }

    @Operation(summary = "설문조사 생성", description = "새로운 설문조사를 생성합니다.")
    @PostMapping
    public ResponseEntity<ServiceResponse<SurveyCreateResponse>> createSurvey(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "설문조사 생성 요청", required = true,
                    content = @Content(
                            schema = @Schema(implementation = SurveyCreateRequest.class),
                            examples = @ExampleObject(value = """
                                        {
                                            "name": "나만의 설문",
                                            "description": "간단한 설문입니다.",
                                            "questions": [
                                                {
                                                    "question": "이름을 알려주세요",
                                                    "description": "당신의 이름은 무엇입니까?",
                                                    "type": "TEXT",
                                                    "required": "REQUIRED",
                                                    "options": [
                                                        {"option" : "입력 1"}
                                                    ]
                                                },
                                                {
                                                    "question": "오늘의 기분을 알려주세요",
                                                    "description": "가벼운 질문부터",
                                                    "type": "SINGLE_SELECT",
                                                    "required": "REQUIRED",
                                                    "options": [
                                                        {"option": "좋아요"},
                                                        {"option": "안좋아요"}
                                                    ]
                                                }
                                            ]
                                        }
                                    """)))
            @Valid @RequestBody SurveyCreateRequest surveyRequest) {
        SurveyCreateResponse createResult = surveyService.createSurvey(surveyRequest);
        return ResponseEntity.ok(ServiceResponse.success(createResult));
    }

    @Operation(summary = "설문조사 수정", description = "생성된 설문조사의 내용을 수정합니다.")
    @PutMapping
    public ResponseEntity<ServiceResponse<SurveyUpdateResponse>> updateSurvey(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "설문조사 수정 요청", required = true, content = @Content(
                    schema = @Schema(implementation = SurveyUpdateRequest.class),
                    examples = @ExampleObject(value = """
                                {
                                    "id": "123e4567-e89b-12d3-a456-426614174000",
                                    "name": "Updated Survey",
                                    "description": "Updated Description",
                                    "questions": [
                                        {
                                            "questionId": 1,
                                            "updateType": "MODIFY",
                                            "question": "Updated Question 1",
                                            "description": "Updated Description 1",
                                            "type": "SINGLE_SELECT",
                                            "required": "REQUIRED",
                                            "options": [
                                                {"option": "Updated Option 1"},
                                                {"option": "Updated Option 2"}
                                            ]
                                        },
                                        {
                                            "questionId": 2,
                                            "updateType": "DELETE"
                                        }
                                    ]
                                }
                            """)))
            @Valid @RequestBody SurveyUpdateRequest surveyRequest) {
        SurveyUpdateResponse updateResult = surveyService.updateSurvey(surveyRequest);
        return ResponseEntity.ok(ServiceResponse.success(updateResult));
    }

    @Operation(summary = "설문조사 응답 제출", description = "대상 설문조사에 응답을 제출합니다.")
    @PostMapping("/{surveyId}/answer")
    public ResponseEntity<ServiceResponse<Void>> submitSurveyAnswer(
            @Parameter(description = "설문조사 ID", required = true) @PathVariable("surveyId") String surveyId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "설문조사 응답 요청", required = true, content = @Content(
                    schema = @Schema(implementation = SubmitSurveyAnswer.class),
                    examples = @ExampleObject(value = """
                                [
                                    {
                                        "questionId": 1,
                                        "answer": ["홍길동"]
                                    },
                                    {
                                        "questionId": 2,
                                        "answer": ["좋아요"]
                                    }
                                ]
                            """)))
            @RequestBody List<SubmitSurveyAnswer> answer) {
        surveyAnswerService.submitSurveyAnswer(surveyId, answer);
        return ResponseEntity.ok(ServiceResponse.success(null));
    }


    @Operation(summary = "설문조사 응답 조회", description = "대상 설문조사에 제출된 응답을 조회합니다.")
    @GetMapping("/{surveyId}/answer/all")
    public ResponseEntity<ServiceResponse<SearchSurveyAnswerResponse>> getAllSurveyAnswersWithQuery(
            @Parameter(description = "설문조사 ID", required = true) @PathVariable("surveyId") String surveyId,
            @Parameter(description = "질문 이름", required = false) @RequestParam(value = "questionName", required = false) String questionName,
            @Parameter(description = "응답 값", required = false) @RequestParam(value = "answer", required = false) String answerValue) {
        SearchSurveyAnswerRequest request = SearchSurveyAnswerRequest.fromRequest(surveyId, questionName, answerValue);
        SearchSurveyAnswerResponse surveyAnswers = surveyAnswerService.getSurveyAnswers(request);
        return ResponseEntity.ok(ServiceResponse.success(surveyAnswers));
    }

}
