package com.survey.www.commons.config.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.www.commons.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class CustomResponseUtil {
    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void sendResponse(HttpServletResponse response, HttpStatus httpStatus, Object object) {
        try {
            ObjectMapper om = new ObjectMapper();
            CommonResponse<?> responseDto = new CommonResponse<>(httpStatus, null, object);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpStatus.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("서버 파싱 에러");
        }
    }
}
