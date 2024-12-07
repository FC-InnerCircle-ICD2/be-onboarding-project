package com.survey.www.surveys.controller;

import com.survey.www.commons.config.validation.ValidationSequence;
import com.survey.www.commons.response.BadRequestResponse;
import com.survey.www.commons.response.CommonResponse;
import com.survey.www.commons.response.InternalServerErrorResponse;
import com.survey.www.commons.response.NotFoundResponse;
import com.survey.www.surveys.dto.request.SurveyAnswerCreateRequest;
import com.survey.www.surveys.dto.request.SurveyAnswerDetailRequest;
import com.survey.www.surveys.dto.request.SurveyCreateRequest;
import com.survey.www.surveys.dto.request.SurveyUpdateRequest;
import com.survey.www.surveys.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Survey Api", description = "설문조사 API")
public interface SurveyApi {
    @Operation(summary = "설문조사 생성 API", description = "설문조사를 생성합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"
                       , content = @Content(schema = @Schema(implementation = SurveyCreateResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"
                       , content = @Content(schema = @Schema(implementation = BadRequestResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"
                       , content = @Content(schema = @Schema(implementation = NotFoundResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"
                       , content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<CommonResponse<SurveyCreateResponse>> createQuestion(@RequestBody @Validated(ValidationSequence.class) SurveyCreateRequest surveyCreateRequest);

    @Operation(summary = "설문조사 상세 조회 TEST", description = "설문조사 질문을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"
                       , content = @Content(schema = @Schema(implementation = SurveyQuestionDetailResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"
                       , content = @Content(schema = @Schema(implementation = BadRequestResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"
                       , content = @Content(schema = @Schema(implementation = NotFoundResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"
                       , content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<CommonResponse<SurveyQuestionDetailResponse>> detailQuestion(@PathVariable("id") Long surveyId);

    @Operation(summary = "설문조사 수정 API", description = "설문조사를 수정합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"
                    , content = @Content(schema = @Schema(implementation = SurveyUpdateResponse.class)
                    , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"
                    , content = @Content(schema = @Schema(implementation = BadRequestResponse.class)
                    , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"
                    , content = @Content(schema = @Schema(implementation = NotFoundResponse.class)
                    , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"
                    , content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class)
                    , mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<CommonResponse<SurveyUpdateResponse>> update(@PathVariable("id") Long surveyId, @RequestBody @Validated(ValidationSequence.class) SurveyUpdateRequest surveyUpdateRequest);

    @Operation(summary = "설문조사 응답 제출 API", description = "설문조사 응답을 제출합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"
                       , content = @Content(schema = @Schema(implementation = SurveyAnswerCreateResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"
                       , content = @Content(schema = @Schema(implementation = BadRequestResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"
                       , content = @Content(schema = @Schema(implementation = NotFoundResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"
                       , content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<CommonResponse<SurveyAnswerCreateResponse>> createAnswer(@PathVariable("id") Long surveyId, @RequestBody @Validated(ValidationSequence.class) SurveyAnswerCreateRequest surveyAnswerCreateRequest);

    @Operation(summary = "설문조사 응답 조회 API", description = "설문조사 응답을 조회합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"
                       , content = @Content(schema = @Schema(implementation = SurveyAnswerDetailResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"
                       , content = @Content(schema = @Schema(implementation = BadRequestResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"
                       , content = @Content(schema = @Schema(implementation = NotFoundResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR"
                       , content = @Content(schema = @Schema(implementation = InternalServerErrorResponse.class)
                       , mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<CommonResponse<List<SurveyAnswerDetailResponse>>> detailAnswer(@PathVariable("id") Long surveyId, SurveyAnswerDetailRequest surveyAnswerDetailRequest);
}
