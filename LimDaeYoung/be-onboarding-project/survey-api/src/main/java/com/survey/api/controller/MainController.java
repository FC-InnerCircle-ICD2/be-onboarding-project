package com.survey.api.controller;

import com.survey.api.constant.CommonConstant;
import com.survey.api.dto.SurveyResponseDto;
import com.survey.api.entity.*;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.*;
import com.survey.api.response.SurveyBaseResponse;
import com.survey.api.response.SurveyItemResponse;
import com.survey.api.response.SurveyOptionResponse;
import com.survey.api.response.SurveyResponse;
import com.survey.api.service.SurveyService;
import com.survey.api.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.DataBindingException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/survey")
public class MainController {
    @Autowired
    SurveyService surveyService;

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Logger getLogger() {
        return logger;
    }

    @PostMapping("/save")
    public ResponseEntity<SurveyBaseResponse> save(@RequestBody @Valid  SurveyForm survey) {
        surveyService.save(survey);
        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("/update/{surveyId}")
    public ResponseEntity<SurveyBaseResponse> update(@PathVariable Long surveyId, @RequestBody @Valid SurveyForm survey, ServletRequest servletRequest) {
        survey.setId(surveyId);
        surveyService.surveyUpdate(survey);
        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("/response/{surveyId}")
    public ResponseEntity<SurveyBaseResponse> reponseSave(@PathVariable Long surveyId, @RequestBody SurveyForm survey, ServletRequest servletRequest) {
        survey.setId(surveyId);

        //필수값 체크
        for(SurveyItemForm item : survey.getItems()) {
            SurveyItemEntity itemEntity = surveyService.findItemByIdAndItemTypeAndSurvey(item.getId(), item.getResponseType(), new SurveyEntity(survey.getId()));

            if (itemEntity == null) {
                throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사 항목에 대해 응답하였습니다.");
            }

            if (CommonConstant.SINGLE_ITEM.equals(item.getResponseType())
                    || CommonConstant.MULTI_ITEM.equals(item.getResponseType())) {
                String[] optionList = item.getAnswer();
                for (String optionId : optionList) {
                    if (surveyService.existsSurveyOptionByIdAndItemId(ConvertUtil.stringToLong(optionId), itemEntity)) {
                        throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 선택지를 선택하였습니다.");
                    }
                }
            }
        }

        surveyService.surveyResponseSave(survey);

        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }


    @PutMapping("/response/select/{surveyId}")
    public ResponseEntity<SurveyBaseResponse<SurveyResponse>> reponseSelect(@PathVariable Long surveyId, @RequestBody ResponseSelectForm selectForm, ServletRequest servletRequest) {
        List<SurveyResponseEntity> responseList = null;
        List<SurveyResponse> surveyResponsesList = new ArrayList<>();

        selectForm.setId(surveyId);

        responseList = surveyService.findResponsesBySurveyIdWithFilters(selectForm.getId(), selectForm.getSearchParam(), selectForm.getPageNumber());

        if(responseList == null || responseList.isEmpty()) {
            return new ResponseEntity<SurveyBaseResponse<SurveyResponse>>( new SurveyBaseResponse<>(CommonConstant.ERR_DATA_NOT_FOUND, "해당 설문지에 대한 답변이 없습니다."), HttpStatus.OK);
        }

        for(SurveyResponseEntity responseDto : responseList) {
            SurveyResponse responseSurvey = new SurveyResponse();
            responseSurvey.setId(responseDto.getId());
            responseSurvey.setDescription(responseDto.getSnapShotDescription());
            responseSurvey.setName(responseDto.getSnapShotDescription());
            responseSurvey.setRegDtm(responseDto.getRegDtm().toString());

            List<SurveyResponseItemEntity> responseItemList = responseDto.getResponseItems();
            List<SurveyItemResponse> surveyItemResponseList = new ArrayList<>();

            for(SurveyResponseItemEntity itemDto : responseItemList) {
                SurveyItemResponse surveyItemResponse = getSurveyItemResponse(itemDto);

                if (CommonConstant.SINGLE_ITEM.equals(itemDto.getItemSnapShotType())
                        || CommonConstant.MULTI_ITEM.equals(itemDto.getItemSnapShotType())) {
                    List<SurveyResponseOptionEntity> optionList = surveyService.findResponseOptionById(itemDto.getId());

                    List<SurveyOptionResponse> responseOptionList = new ArrayList<>();

                    for (SurveyResponseOptionEntity optionEntity : optionList) {
                        SurveyOptionResponse surveyOptionResponse = new SurveyOptionResponse();
                        surveyOptionResponse.setId(optionEntity.getId());
                        surveyOptionResponse.setOptionName(optionEntity.getOptionSnapShotName());
                        surveyOptionResponse.setUseYn(optionEntity.getOptionSnapShotUseYn());
                        responseOptionList.add(surveyOptionResponse);
                    }

                    surveyItemResponse.setSelectOptions(responseOptionList);
                }

                surveyItemResponseList.add(surveyItemResponse);

            }
            responseSurvey.setItemList(surveyItemResponseList);
            surveyResponsesList.add(responseSurvey);
        }

        return new ResponseEntity<SurveyBaseResponse<SurveyResponse>>( new SurveyBaseResponse<>(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS, surveyResponsesList), HttpStatus.OK);
    }

    private static SurveyItemResponse getSurveyItemResponse(SurveyResponseItemEntity itemDto) {
        SurveyItemResponse surveyItemResponse = new SurveyItemResponse();
        surveyItemResponse.setId(itemDto.getId());
        surveyItemResponse.setDescription(itemDto.getItemSnapShotDescription());
        surveyItemResponse.setItemType(itemDto.getItemSnapShotType());
        surveyItemResponse.setAnswer(itemDto.getAnswerText());
        surveyItemResponse.setUseYn(itemDto.getItemSnapShotUseYn());
        surveyItemResponse.setItemName(itemDto.getItemSnapShotName());
        surveyItemResponse.setRegDtm(itemDto.getRegDtm().toString());
        surveyItemResponse.setRequired(itemDto.isItemSnapShotRequired());
        return surveyItemResponse;
    }

    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    @ResponseStatus(HttpStatus.OK)
    public SurveyBaseResponse onHttpMediaTypeNotSupportedException(HttpServletRequest req, HttpServletResponse response, Exception e) {
        getLogger().error("ContentTypeException: ", e);
        return new SurveyBaseResponse(CommonConstant.ERR_CONTENT_TYPE_ERROR, CommonConstant.ERR_MSG_CONTENT_TYPE_ERROR);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    @ResponseStatus(HttpStatus.OK)
    public SurveyBaseResponse onHttpMessageNotReadableException(HttpServletRequest req, HttpServletResponse response, Exception e) {
        getLogger().error("JSONException: ", e);
        return new SurveyBaseResponse(CommonConstant.ERR_JSON_ERROR, CommonConstant.ERR_MSG_JSON_ERROR);
    }

    @ExceptionHandler({ UncategorizedSQLException.class, DataBindingException.class, DataAccessException.class,
            DuplicateKeyException.class, Exception.class })
    @ResponseStatus(HttpStatus.OK)
    public SurveyBaseResponse onDataException(HttpServletRequest req, HttpServletResponse response, Exception e) {
        getLogger().error("DataException: ", e);
        return new SurveyBaseResponse(CommonConstant.ERR_DB_DATA_ERROR, e.getMessage());
    }

    @ExceptionHandler(SurveyApiException.class)
    @ResponseStatus(HttpStatus.OK)
    public SurveyBaseResponse onApiException(HttpServletRequest req, SurveyApiException e) {
        getLogger().error("SurveyApiException: ", e);

        return new SurveyBaseResponse(e.getCode(), e.getMessage());
    }
}

