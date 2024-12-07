package net.gentledot.survey.application.service;

import lombok.extern.slf4j.Slf4j;
import net.gentledot.survey.application.service.in.model.request.SearchSurveyAnswerRequest;
import net.gentledot.survey.application.service.in.model.request.SubmitSurveyAnswer;
import net.gentledot.survey.application.service.in.model.response.SearchSurveyAnswerResponse;
import net.gentledot.survey.application.service.in.model.response.SurveyAnswerValue;
import net.gentledot.survey.application.service.out.SurveyAnswerRepository;
import net.gentledot.survey.application.service.out.SurveyRepository;
import net.gentledot.survey.domain.exception.ServiceError;
import net.gentledot.survey.domain.exception.SurveyNotFoundException;
import net.gentledot.survey.domain.surveyanswer.SurveyAnswer;
import net.gentledot.survey.domain.surveyanswer.SurveyAnswerSubmission;
import net.gentledot.survey.domain.surveyanswer.dto.SubmitSurveyAnswerDto;
import net.gentledot.survey.domain.surveybase.Survey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static net.gentledot.survey.application.service.util.SurveyValidator.validateSurveyAnswers;

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
    public void submitSurveyAnswer(String surveyId, List<SubmitSurveyAnswer> answers) {
        Survey survey = surveyRepository.findById(surveyId);

        // 설문조사 항목과 응답 값 검증
        validateSurveyAnswers(survey, answers);

        List<SubmitSurveyAnswerDto> collectedSubmitAnswers = answers.stream()
                .map(SubmitSurveyAnswerDto::from)
                .collect(Collectors.toList());

        SurveyAnswer surveyAnswer = SurveyAnswer.of(survey, collectedSubmitAnswers);
        surveyAnswerRepository.save(surveyAnswer);
    }

    @Transactional(readOnly = true)
    public SearchSurveyAnswerResponse getSurveyAnswers(SearchSurveyAnswerRequest request) {
        String surveyId = request.getSurveyId();
        boolean isExists = surveyRepository.existsById(surveyId);

        if (!isExists) {
            throw new SurveyNotFoundException(ServiceError.INQUIRY_SURVEY_NOT_FOUND);
        }

        List<SurveyAnswer> allSurveyAnswers = surveyAnswerRepository.findAllBySurveyId(surveyId);

        List<SurveyAnswerValue> answerValues = allSurveyAnswers.stream()
                .map(surveyAnswer -> {
                    List<SurveyAnswerSubmission> answers = surveyAnswer.getAnswers();
                    return SurveyAnswerValue.of(surveyAnswer.getId(), answers);
                })
                .collect(Collectors.toList());

        return new SearchSurveyAnswerResponse(surveyId, answerValues);
    }


}
