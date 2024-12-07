package com.practice.survey.response.service;

import com.practice.survey.common.response.ResponseTemplate;
import com.practice.survey.common.response.StatusEnum;
import com.practice.survey.response.model.dto.ResponseSaveRequestDto;

public interface ResponseService {

    public ResponseTemplate<StatusEnum> createResponse(ResponseSaveRequestDto responseSaveRequestDto);

}
