package com.survey.api.controller;

import com.survey.api.constant.CommonConstant;
import com.survey.api.entity.SurveyResponseEntity;
import com.survey.api.entity.SurveyResponseItemEntity;
import com.survey.api.form.ResponseSelectForm;
import com.survey.api.form.SurveyForm;
import com.survey.api.response.SurveyBaseResponse;
import com.survey.api.response.SurveyResponse;
import com.survey.api.service.SurveyService;
import com.survey.api.util.VoConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/survey")
public class MainController extends BaseController {

    @Autowired
    SurveyService surveyService;

    @PostMapping("/save")
    public ResponseEntity<SurveyBaseResponse> save(@RequestBody @Valid  SurveyForm survey) {
        surveyService.save(survey);
        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("/update/{surveyId}")
    public ResponseEntity<SurveyBaseResponse> update(@PathVariable Long surveyId, @RequestBody @Valid SurveyForm survey, ServletRequest servletRequest) {
        survey.setId(surveyId);
        surveyService.itemValidator(survey);
        surveyService.surveyUpdate(survey);
        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("/response/{surveyId}")
    public ResponseEntity<SurveyBaseResponse> reponseSave(@PathVariable  Long surveyId, @RequestBody SurveyForm survey, ServletRequest servletRequest) {
        survey.setId(surveyId);
        surveyService.itemValidator(survey);
        surveyService.surveyResponseSave(survey);
        return new ResponseEntity<SurveyBaseResponse>(new SurveyBaseResponse(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS), HttpStatus.OK);
    }


    @PutMapping("/response/select/{surveyId}")
    public ResponseEntity<SurveyBaseResponse<SurveyResponse>> reponseSelect(@PathVariable Long surveyId, @RequestBody ResponseSelectForm selectForm, ServletRequest servletRequest) {
        selectForm.setId(surveyId);

        List<SurveyResponseEntity> entityList = surveyService.findResponsesBySurveyIdWithFilters(selectForm.getId(), selectForm.getSearchParam(), selectForm.getPageNumber());

        if(entityList == null || entityList.isEmpty()) {
            return new ResponseEntity<SurveyBaseResponse<SurveyResponse>>( new SurveyBaseResponse<>(CommonConstant.ERR_DATA_NOT_FOUND, "해당 설문지에 대한 답변이 없습니다."), HttpStatus.OK);
        }

        for(SurveyResponseEntity responseDto : entityList) {
            List<SurveyResponseItemEntity> responseItemList = responseDto.getResponseItems();
            for(SurveyResponseItemEntity itemDto : responseItemList) {
                if (CommonConstant.SINGLE_ITEM.equals(itemDto.getItemSnapShotType()) || CommonConstant.MULTI_ITEM.equals(itemDto.getItemSnapShotType())) {
                    itemDto.setResponseOption(surveyService.findResponseOptionById(itemDto.getId()));
                }
            }
        }

        List<SurveyResponse> responseList = VoConvertUtil.entityToVo(entityList);

        return new ResponseEntity<SurveyBaseResponse<SurveyResponse>>( new SurveyBaseResponse<>(CommonConstant.ERR_SUCCESS, CommonConstant.ERR_MSG_SUCCESS, responseList), HttpStatus.OK);
    }
}

