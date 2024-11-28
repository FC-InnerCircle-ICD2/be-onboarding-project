package com.innercircle.query.config.http;

import com.innercircle.common.config.http.BaseExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackages = {
		"com.innercircle.query.controller"
})
public class GeneralExceptionHandler extends BaseExceptionHandler {

}
