package com.survey.api.controller;

import com.survey.api.constant.CommonConstant;
import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyItemEntity;
import com.survey.api.entity.SurveyOptionEntity;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.*;
import com.survey.api.response.SurveyBaseResponse;
import com.survey.api.service.SurveyService;
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
            SurveyEntity surveyResult = surveyService.sureveySave(new SurveyEntity(survey.getName(), survey.getDescription(), CommonConstant.Y));

            for(SurveyItemForm itemForm : survey.getItems()){
                SurveyItemEntity itemEntity = surveyService.itemSave(new SurveyItemEntity( itemForm.getItemName(), itemForm.getDescription(), itemForm.getItemType(), itemForm.isRequired(), CommonConstant.Y, surveyResult));

                if(CommonConstant.SINGLE_ITEM.equals(itemForm.getItemType()) || CommonConstant.MULTI_ITEM.equals(itemForm.getItemType())) {
                    if(itemForm.getOptionList() != null) {
                        for (SurveyOptionForm optionForm : itemForm.getOptionList()) {
                            surveyService.optionSave(new SurveyOptionEntity(optionForm.getOptionName(), optionForm.getOptionOrder(), CommonConstant.Y, itemEntity));
                        }
                    }
                }
            }
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

        if (!surveyService.existsSurveyById(survey.getId())) {
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

                if (!CommonConstant.ACTION_TYPE_CREATE.equals(item.getActionType()) && !surveyService.existsSurveyItemById(item.getId())) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사 항목에 대한 수정 요청을 하였습니다.");
                }

                if(item.getOptionList() != null) {
                    for(SurveyOptionUpdateForm option : item.getOptionList()) {
                        if (!CommonConstant.ACTION_TYPE_CREATE.equals(option.getActionType()) && !surveyService.existsSurveyOptionById(option.getId())) {
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

//    @GetMapping("/{id}")
//    public ResponseEntity<SurveyEntity> search(@PathVariable Long id) {
//        SurveyEntity survey = surveyService.getSurveyWithDetails(id);
//        return new ResponseEntity<SurveyEntity>(survey, HttpStatus.OK);
//    }


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

