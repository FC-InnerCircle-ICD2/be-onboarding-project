package net.gentledot.survey.application.service;


import net.gentledot.survey.application.service.in.model.request.SurveyCreateRequest;
import net.gentledot.survey.application.service.in.model.request.SurveyUpdateRequest;
import net.gentledot.survey.application.service.in.model.response.SurveyCreateResponse;
import net.gentledot.survey.application.service.in.model.response.SurveyUpdateResponse;
import net.gentledot.survey.application.service.out.SurveyOut;
import net.gentledot.survey.application.service.util.SurveyVaildator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SurveyService {
    private final SurveyOut surveyOut;

    public SurveyService(SurveyOut surveyOut) {
        this.surveyOut = surveyOut;
    }

    @Transactional
    public SurveyCreateResponse createSurvey(SurveyCreateRequest surveyRequest) {
        SurveyVaildator.validateRequest(surveyRequest);
        return surveyOut.createSurvey(surveyRequest);
    }

    @Transactional
    public SurveyUpdateResponse updateSurvey(SurveyUpdateRequest surveyRequest) {
        SurveyVaildator.validateRequest(surveyRequest);

        return surveyOut.updateSurvey(surveyRequest);
    }
}
