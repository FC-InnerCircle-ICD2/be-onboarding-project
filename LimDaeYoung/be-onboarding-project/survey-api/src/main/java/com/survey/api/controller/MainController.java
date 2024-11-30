package com.survey.api.controller;

import com.survey.api.constant.CommonConstant;
import com.survey.api.dto.SurveyResponseDto;
import com.survey.api.dto.SurveyResponseItemDto;
import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyItemEntity;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.*;
import com.survey.api.response.SurveyBaseResponse;
import com.survey.api.response.SurveyItemResponse;
import com.survey.api.response.SurveyResponse;
import com.survey.api.service.SurveyService;
import com.survey.api.util.ConvertUtil;
import io.micrometer.core.instrument.util.StringUtils;
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
    public ResponseEntity<SurveyBaseResponse> save(@RequestBody SurveyForm survey) {
        //필수값 체크
        if (StringUtils.isBlank(survey.getName())) {
            throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "survey name");
        }

        if (StringUtils.isBlank(survey.getDescription())) {
            throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "survey description");
        }

        if(survey.getItems() != null) {
            for (SurveyItemForm item : survey.getItems()) {
                if (StringUtils.isBlank(item.getItemName())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item Name");
                }

                if (StringUtils.isBlank(item.getDescription())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item description");
                }

                if (StringUtils.isBlank(item.getItemType())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item type");
                }

                if (!(CommonConstant.SINGLE_ITEM.equals(item.getItemType())
                        || CommonConstant.MULTI_ITEM.equals(item.getItemType())
                        || CommonConstant.SHORT_ITEM.equals(item.getItemType())
                        || CommonConstant.LONG_ITEM.equals(item.getItemType()))) {
                    throw new SurveyApiException(CommonConstant.ERR_FAIL, CommonConstant.ERROR_TYPE_REQUEST_INVALID + " item type");
                }

                if(item.getOptionList() != null) {
                    for(SurveyOptionForm option : item.getOptionList()) {
                        if (StringUtils.isBlank(option.getOptionName())) {
                            throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "option Name");
                        }
                    }
                }
            }
        }

        try {
            //DB insert
            surveyService.save(survey);
        } catch (Exception e){
            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ERROR, CommonConstant.ERR_MSG_DB_DATA_ERROR);
        }

        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<SurveyBaseResponse> update(@RequestBody SurveyUpdateForm survey, ServletRequest servletRequest) {
        //필수값 체크
        if (StringUtils.isBlank(survey.getName())) {
            throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "survey name");
        }

        if (StringUtils.isBlank(survey.getDescription())) {
            throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "survey description");
        }

        if (surveyService.existsSurveyById(survey.getId())) {
            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사에 대한 수정 요청을 하였습니다.");
        }

        if(survey.getItems() != null) {
            for (SurveyItemUpdateForm item : survey.getItems()) {
                if (StringUtils.isBlank(item.getItemName())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item Name");
                }

                if (StringUtils.isBlank(item.getDescription())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item description");
                }

                if (StringUtils.isBlank(item.getItemType())) {
                    throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "item type");
                }

                if (!(CommonConstant.SINGLE_ITEM.equals(item.getItemType())
                        || CommonConstant.MULTI_ITEM.equals(item.getItemType())
                        || CommonConstant.SHORT_ITEM.equals(item.getItemType())
                        || CommonConstant.LONG_ITEM.equals(item.getItemType()))) {
                    throw new SurveyApiException(CommonConstant.ERR_FAIL, CommonConstant.ERROR_TYPE_REQUEST_INVALID + " item type");
                }

                //신규 추가가 아니면 기존에 있었던 항목인지 체크
                if (!CommonConstant.ACTION_TYPE_CREATE.equals(item.getActionType()) && surveyService.existsByIdAndSurvey(item.getId(), new SurveyEntity(survey.getId()))) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사 항목에 대한 수정 요청을 하였습니다.");
                }

                if(item.getOptionList() != null) {
                    for(SurveyOptionUpdateForm option : item.getOptionList()) {
                        //신규 추가가 아니면 기존에 있었던 옵션인지 체크
                        if (!CommonConstant.ACTION_TYPE_CREATE.equals(option.getActionType()) && surveyService.existsSurveyOptionByIdAndItemId(option.getId(), new SurveyItemEntity(item.getId()))) {
                            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사 항목에 대한 수정 요청을 하였습니다.");
                        }

                        if (StringUtils.isBlank(option.getOptionName())) {
                            throw new SurveyApiException(CommonConstant.ERR_DATA_NOT_FOUND, CommonConstant.ERR_MSG_DATA_NOT_FOUND + "option Name");
                        }
                    }
                }
            }
        }

        try{
            surveyService.surveyUpdate(survey);
        } catch (SurveyApiException e){
            throw new SurveyApiException(e.getCode(), e.getMessage());
        } catch (Exception e){
            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ERROR, CommonConstant.ERR_MSG_DB_DATA_ERROR);
        }

        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }

    @PostMapping("/response/save")
    public ResponseEntity<SurveyBaseResponse> reponseSave(@RequestBody SurveyResponseForm survey, ServletRequest servletRequest) {
        //필수값 체크 및 응답 형식에 맞는 대답을 했는지 확인
        if (surveyService.existsSurveyById(survey.getId())) {
            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사에 대한 응답 요청을 하였습니다.");
        }

        if(survey.getItems() != null) {
            if(surveyService.countItemBySurvey(new SurveyEntity(survey.getId())) != survey.getItems().size()) {
                throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "설문지와 응답 항목이 일치하지 않습니다.");
            }

            for (SurveyResponseItemForm item : survey.getItems()) {
                if (StringUtils.isEmpty(item.getReponseType())) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "응답 타입이 누락되었습니다.");
                }

                SurveyItemEntity itemEntity = surveyService.findItemByIdAndItemTypeAndSurvey(item.getId(), item.getReponseType(), new SurveyEntity(survey.getId()));

                if (itemEntity == null) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사 항목에 대해 응답하였습니다.");
                }

                if (!item.getReponseType().equals(itemEntity.getItemType())) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "용청하신 응답타입이 실제 응답타입과 일치하지 않습니다.");
                }

                if(itemEntity.isRequired() && (item.getAnswer() == null || item.getAnswer().length == 0)) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, itemEntity.getItemName() + " 항목은 필수 입니다.");
                }

                if (CommonConstant.SINGLE_ITEM.equals(item.getReponseType())
                        || CommonConstant.MULTI_ITEM.equals(item.getReponseType())) {
                    String[] optionList = item.getAnswer();
                    for (String optionId : optionList) {
                        if (surveyService.existsSurveyOptionByIdAndItemId(ConvertUtil.stringToLong(optionId), itemEntity)) {
                            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 선택지를 선택하였습니다.");
                        }
                    }
                }
            }
        }

        try{
            surveyService.surveyResponseSave(survey);
        } catch (SurveyApiException e){
            throw new SurveyApiException(e.getCode(), e.getMessage());
        } catch (Exception e){
            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ERROR, CommonConstant.ERR_MSG_DB_DATA_ERROR);
        }

        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }


    @PostMapping("/response/select")
    public ResponseEntity<SurveyBaseResponse<SurveyResponse>> reponseSelect(@RequestBody ResponseSelectForm selectForm, ServletRequest servletRequest) {
        List<SurveyResponseDto> responseList = null;
        List<SurveyResponse> surveyResponsesList = new ArrayList<>();

        try{
            responseList = surveyService.findResponsesBySurveyIdWithFilters(selectForm.getId(), selectForm.getSearchParam(), selectForm.getPageNumber());

            if(responseList == null || responseList.isEmpty()) {
                return new ResponseEntity<SurveyBaseResponse<SurveyResponse>>( new SurveyBaseResponse<>(CommonConstant.ERR_DATA_NOT_FOUND, "해당 설문지에 대한 답변이 없습니다."), HttpStatus.OK);
            }

            for(SurveyResponseDto responseDto : responseList) {
                SurveyResponse responseSurvey = new SurveyResponse();
                responseSurvey.setId(responseDto.getId());
                responseSurvey.setDescription(responseDto.getDescription());
                responseSurvey.setName(responseDto.getName());
                responseSurvey.setUseYn(responseDto.getUseYn());
                responseSurvey.setRegDtm(responseDto.getRegDtm());

                List<SurveyResponseItemDto> responseItemList = surveyService.findResponseItemByFilters(responseDto.getId(), selectForm.getSearchParam());
                List<SurveyItemResponse> surveyItemResponseList = new ArrayList<>();

                for(SurveyResponseItemDto itemDto : responseItemList) {
                    SurveyItemResponse surveyItemResponse = new SurveyItemResponse();
                    surveyItemResponse.setId(itemDto.getId());
                    surveyItemResponse.setDescription(itemDto.getDescription());
                    surveyItemResponse.setItemType(itemDto.getItemType());
                    surveyItemResponse.setAnswer(itemDto.getAnswer());
                    surveyItemResponse.setUseYn(itemDto.getUseYn());
                    surveyItemResponse.setItemName(itemDto.getItemName());
                    surveyItemResponse.setRegDtm(itemDto.getRegDtm());
                    surveyItemResponse.setRequired(itemDto.isRequired());
                    surveyItemResponseList.add(surveyItemResponse);
                }
                responseSurvey.setItemList(surveyItemResponseList);
                surveyResponsesList.add(responseSurvey);
            }
        } catch (SurveyApiException e){
            e.printStackTrace();
            throw new SurveyApiException(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ERROR, CommonConstant.ERR_MSG_DB_DATA_ERROR);
        }

        return new ResponseEntity<SurveyBaseResponse<SurveyResponse>>( new SurveyBaseResponse<>(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS, surveyResponsesList), HttpStatus.OK);
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
        return new SurveyBaseResponse(CommonConstant.ERR_DB_DATA_ERROR, CommonConstant.ERR_MSG_DB_DATA_ERROR);
    }

    @ExceptionHandler(SurveyApiException.class)
    @ResponseStatus(HttpStatus.OK)
    public SurveyBaseResponse onApiException(HttpServletRequest req, SurveyApiException e) {
        getLogger().error("SurveyApiException: ", e);

        return new SurveyBaseResponse(e.getCode(), e.getMessage());
    }
}

