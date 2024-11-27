package net.gentledot.survey.service;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.dto.request.SubmitSurveyAnswer;
import net.gentledot.survey.exception.ServiceError;
import net.gentledot.survey.exception.SurveyNotFoundException;
import net.gentledot.survey.model.entity.Survey;
import net.gentledot.survey.model.entity.SurveyAnswer;
import net.gentledot.survey.repository.SurveyAnswerRepository;
import net.gentledot.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SurveyAnswerService {
    private final SurveyRepository surveyRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;

    public SurveyAnswerService(SurveyRepository surveyRepository, SurveyAnswerRepository surveyAnswerRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyAnswerRepository = surveyAnswerRepository;
    }

    @Transactional
    public void submitSurveyResponse(String surveyId, List<SubmitSurveyAnswer> answers) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND));

        // 설문조사 항목과 응답 값 검증
        validateSurveyAnswers(survey, answers);

        SurveyAnswer surveyAnswer = SurveyAnswer.of(survey, answers);
        surveyAnswerRepository.save(surveyAnswer);
    }

    private void validateSurveyAnswers(Survey survey, List<SubmitSurveyAnswer> answers) {
    }
}
