package net.gentledot.survey.application.service;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.application.service.in.model.request.SearchSurveyAnswerRequest;
import net.gentledot.survey.application.service.in.model.request.SubmitSurveyAnswer;
import net.gentledot.survey.application.service.in.model.response.SearchSurveyAnswerResponse;
import net.gentledot.survey.application.service.in.model.response.SurveyAnswerValue;
import net.gentledot.survey.application.service.out.SurveyAnswerOut;
import net.gentledot.survey.application.service.out.SurveyOut;
import net.gentledot.survey.domain.exception.ServiceError;
import net.gentledot.survey.domain.exception.SurveyNotFoundException;
import net.gentledot.survey.domain.surveyanswer.SurveyAnswer;
import net.gentledot.survey.domain.surveyanswer.SurveyAnswerSubmission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SurveyAnswerService {
    private final SurveyOut surveyOut;
    private final SurveyAnswerOut surveyAnswerOut;

    public SurveyAnswerService(SurveyOut surveyOut, SurveyAnswerOut surveyAnswerOut) {
        this.surveyOut = surveyOut;
        this.surveyAnswerOut = surveyAnswerOut;
    }


    @Transactional
    public void submitSurveyAnswer(String surveyId, List<SubmitSurveyAnswer> answers) {
        surveyAnswerOut.save(surveyId, answers);
    }

    @Transactional(readOnly = true)
    public SearchSurveyAnswerResponse getSurveyAnswers(SearchSurveyAnswerRequest request) {
        String surveyId = request.getSurveyId();
        boolean isExists = surveyOut.existsById(surveyId);

        if (!isExists) {
            throw new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND);
        }

        List<SurveyAnswer> allSurveyAnswers = surveyAnswerOut.findAllBySurveyId(surveyId);

        List<SurveyAnswerValue> answerValues = allSurveyAnswers.stream()
                .map(surveyAnswer -> {
                    List<SurveyAnswerSubmission> answers = surveyAnswer.getAnswers();
                    return SurveyAnswerValue.of(surveyAnswer.getId(), answers);
                })
                .collect(Collectors.toList());

        return new SearchSurveyAnswerResponse(surveyId, answerValues);
    }


}
