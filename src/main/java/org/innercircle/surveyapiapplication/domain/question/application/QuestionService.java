package org.innercircle.surveyapiapplication.domain.question.application;

import lombok.RequiredArgsConstructor;
import org.innercircle.surveyapiapplication.domain.question.domain.Question;
import org.innercircle.surveyapiapplication.domain.question.infrastructure.QuestionRepository;
import org.innercircle.surveyapiapplication.domain.survey.domain.Survey;
import org.innercircle.surveyapiapplication.domain.survey.infrastructure.SurveyRepository;
import org.innercircle.surveyapiapplication.domain.survey.presentation.dto.QuestionUpdateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    public Question createQuestion(Long surveyId, Question question) {
        Survey survey = surveyRepository.findById(surveyId);
        survey.addQuestion(question);
        question = questionRepository.save(question);
        return question;
    }

    public Question findByIdAndVersion(Long questionId, int questionVersion) {
        return questionRepository.findByIdAndVersion(questionId, questionVersion);
    }

    // Todo: 낙관적 락 고민
    public Question updateQuestion(Long questionId, QuestionUpdateRequest request) {
        Question question = questionRepository.findLatestQuestionById(questionId);
        return question.update(request.name(), request.description(), request.type(), request.required(), request.options());
    }

}
