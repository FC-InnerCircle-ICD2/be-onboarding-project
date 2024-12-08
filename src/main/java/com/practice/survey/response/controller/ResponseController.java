package com.practice.survey.response.controller;

import com.practice.survey.common.response.ResponseTemplate;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.response.model.dto.ResponseSaveRequestDto;
import com.practice.survey.response.service.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/response")
@RequiredArgsConstructor
public class ResponseController implements ResponseControllerDocs {

    private final ResponseService responseService;

    @Override
    public ResponseTemplate<StatusEnum> createResponse(@Valid @RequestBody ResponseSaveRequestDto responseSaveRequestDto) {
        return responseService.createResponse(responseSaveRequestDto);
    }

}
