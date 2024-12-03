package com.survey.api.interceptor;

import com.survey.api.constant.CommonConstant;
import com.survey.api.entity.SurveyEntity;
import com.survey.api.entity.SurveyItemEntity;
import com.survey.api.exception.SurveyApiException;
import com.survey.api.form.SurveyItemForm;
import com.survey.api.service.SurveyService;
import com.survey.api.util.ConvertUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static String SURVEY_AUTH_URL = "/survey/update/{surveyId}";
    private static String SURVEY_RESPONSE_AUTH_URL = "/survey/response/{surveyId}";
    private static String SURVEY_SELECT_AUTH_URL = "/survey/response/select/{surveyId}";

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private final SurveyService surveyService;

    public AuthInterceptor(SurveyService surveyService) {
        this.surveyService = surveyService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (pathMatcher.match(SURVEY_AUTH_URL, uri) || pathMatcher.match(SURVEY_RESPONSE_AUTH_URL, uri)
            || pathMatcher.match(SURVEY_SELECT_AUTH_URL, uri)) {
            Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            if (pathVariables != null && pathVariables.containsKey("surveyId")) {
                String surveyId = pathVariables.get("surveyId");

                if (surveyService.existsSurveyById(ConvertUtil.stringToLong(surveyId))) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "존재 하지 않는 설문 조사에 대한 수정 요청을 하였습니다.");
                }

                List<SurveyItemForm> itemForm = (List<SurveyItemForm>)request.getAttribute("items");

                if(surveyService.countBySurveyAndUseYn(new SurveyEntity(ConvertUtil.stringToLong(surveyId)), CommonConstant.Y) != itemForm.size()) {
                    throw new SurveyApiException(CommonConstant.ERR_DB_DATA_ID_ERROR, "설문지와 응답 항목이 일치하지 않습니다.");
                }
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("#################### postHandle");

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
