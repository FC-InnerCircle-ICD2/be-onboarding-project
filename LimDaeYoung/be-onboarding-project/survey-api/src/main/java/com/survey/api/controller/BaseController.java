package com.survey.api.controller;

import com.survey.api.constant.CommonConstant;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.response.SurveyBaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DataBindingException;
import java.lang.invoke.MethodHandles;

public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Logger getLogger() {
        return logger;
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
