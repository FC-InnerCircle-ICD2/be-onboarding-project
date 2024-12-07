package com.innercicle.adapter.in.web.survey.v1;

import com.innercicle.adapter.in.web.survey.v1.request.ModifySurveyRequestV1;
import com.innercicle.adapter.in.web.survey.v1.response.ModifySurveyResponseV1;
import com.innercicle.application.port.in.v1.ModifySurveyUseCaseV1;
import com.innercicle.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.innercicle.utils.ApiUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/survey")
public class ModifySurveyControllerV1 {

    private final ModifySurveyUseCaseV1 modifySurveyUseCaseV1;

    @PatchMapping
    public ApiUtil.ApiResult<ModifySurveyResponseV1> modifySurvey(@RequestBody ModifySurveyRequestV1 request) {
        return success(ModifySurveyResponseV1.from(modifySurveyUseCaseV1.modifySurvey(request.mapToCommand())));
    }

}
