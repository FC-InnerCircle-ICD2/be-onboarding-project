package com.survey.api.handler;

import com.survey.api.response.SurveyErrorResult;
import com.survey.api.util.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;

@RestController
public class SurveyErrorHandler implements ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@GetMapping("/surveyError")
	public Object handleGetError(HttpServletRequest request, HttpServletResponse response) {
		return toSurveyResult(request, response);
	}

	@PostMapping("/surveyError")
	public Object handlePostError(HttpServletRequest request, HttpServletResponse response) {
		return toSurveyResult(request, response);
	}

	@PutMapping("/surveyError")
	public Object handlePutError(HttpServletRequest request, HttpServletResponse response) {
		return toSurveyResult(request, response);
	}

	@DeleteMapping("/surveyError")
	public Object handleDeleteError(HttpServletRequest request, HttpServletResponse response) {
		return toSurveyResult(request, response);
	}

	public Object toSurveyResult(HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(HttpStatus.OK.value());
		response.setHeader("Content-Type", "application/json;charset=UTF-8");

		int statusCode = 0;
		String statusMessage = null;

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Object uri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		if (status != null) {
			statusCode = ConvertUtil.stringToInt(status.toString());

			logger.info("statusCode: " + statusCode);
			logger.info("uri: " + uri.toString());

			if(HttpStatus.NOT_FOUND.value() == statusCode) {
				statusMessage = "URI가 존재하지 않습니다.";
			} else if(HttpStatus.METHOD_NOT_ALLOWED.value() == statusCode) {
				statusMessage = "지원하지 않는 METHOD를 사용하였습니다.";
			}
		} else {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			statusMessage = "알 수 없는 에러가 발생하였습니다.";
		}

		Object result = toSurveyResult(request, response, statusCode, statusMessage);

		logger.info("object: " + ConvertUtil.objectToJsonString(result));
		return result;
	}

	public Object toSurveyResult(HttpServletRequest request, HttpServletResponse response, int statusCode, String statusMessage) {
		String accessUri = "[" + request.getMethod() + "] " + request.getRequestURI();
		logger.info("accessUri: " + accessUri);

		return new SurveyErrorResult(statusCode, statusMessage);
	}
}