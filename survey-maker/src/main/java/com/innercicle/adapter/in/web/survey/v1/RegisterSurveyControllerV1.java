package com.innercicle.adapter.in.web.survey.v1;

import com.innercicle.adapter.in.web.survey.v1.request.RegisterSurveyRequestV1;
import com.innercicle.adapter.in.web.survey.v1.response.RegisterSurveyResponseV1;
import com.innercicle.application.port.in.v1.RegisterSurveyUseCaseV1;
import com.innercicle.domain.v1.Survey;
import com.innercicle.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.innercicle.utils.ApiUtil.success;

/**
 * 설문 등록 컨트롤러
 *
 * @author seunggu.lee
 */
@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class RegisterSurveyControllerV1 {

    private final RegisterSurveyUseCaseV1 registerSurveyUseCaseV1;

    @PostMapping
    public ApiUtil.ApiResult<RegisterSurveyResponseV1> registerSurvey(@RequestBody RegisterSurveyRequestV1 request) {
        Survey survey = registerSurveyUseCaseV1.registerSurvey(request.mapToCommand());
        return success(RegisterSurveyResponseV1.from(survey));
    }

}
