package com.survey.api.interceptor;

import com.survey.api.constant.CommonConstant;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.service.SurveyService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final SurveyService surveyService;

    private static String SURVEY_AUTH_URL = "/survey/{surveyId}";

    public AuthInterceptor(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("#################### preHandle");

        Long surveyId = Long.getLong(new AntPathMatcher().extractUriTemplateVariables(SURVEY_AUTH_URL, request.getRequestURI()).get("surveyId"));

        if (surveyService.existsSurveyById(surveyId)) {
            throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사에 대한 수정 요청을 하였습니다.");
        }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("#################### postHandle");

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
