package com.innercicle.adapter.in.web.reply.v1;

import com.innercicle.adapter.in.web.reply.v1.request.ReplySurveyRequestV1;
import com.innercicle.adapter.in.web.reply.v1.response.ReplySurveyResponse;
import com.innercicle.application.port.in.v1.ReplySurveyUsecaseV1;
import com.innercicle.utils.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.innercicle.utils.ApiUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/survey/reply")
public class ReplySurveyControllerV1 {

    private final ReplySurveyUsecaseV1 replySurveyUsecaseV1;

    @PostMapping
    public ApiUtil.ApiResult<ReplySurveyResponse> replySurvey(@RequestBody ReplySurveyRequestV1 request) {
        return success(ReplySurveyResponse.from(replySurveyUsecaseV1.replySurvey(request.mapToCommand())));
    }

}
