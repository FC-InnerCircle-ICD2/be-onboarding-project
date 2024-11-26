package com.icd.survey.common;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtils {
    public static String getRequestIp() {
        if(RequestContextHolder.getRequestAttributes() == null){
            return Strings.EMPTY;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            if (request.getRemoteAddr() == null) {
                ip = request.getRemoteAddr();
            } else {
                ip = Strings.EMPTY;
            }

        }
        return ip;
    }
}
