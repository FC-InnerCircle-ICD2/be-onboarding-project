package com.metsakurr.beonboardingproject.domain.answer.service;

import com.metsakurr.beonboardingproject.common.enums.ResponseCode;
import com.metsakurr.beonboardingproject.common.exception.ServiceException;
import com.metsakurr.beonboardingproject.domain.answer.dto.CreateAnswerRequest;
import com.metsakurr.beonboardingproject.domain.answer.entity.Answer;
import com.metsakurr.beonboardingproject.domain.answer.entity.Response;
import com.metsakurr.beonboardingproject.domain.answer.repository.AnswerRepository;
import com.metsakurr.beonboardingproject.domain.answer.repository.ResponseRepository;
import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.metsakurr.beonboardingproject.domain.survey.repository.OptionRepository;
import com.metsakurr.beonboardingproject.domain.survey.repository.QuestionRepository;
import com.metsakurr.beonboardingproject.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final AnswerRepository answerRepository;
    private final ResponseRepository responseRepository;

    @Transactional
    public void create(CreateAnswerRequest request) {
        long surveyIdx = request.getIdx();
        Survey survey = surveyRepository.findById(surveyIdx);

        if (survey == null) {
            throw new ServiceException(ResponseCode.NOT_FOUND_SURVEY);
        }

        List<Question> questions = questionRepository.findAllBySurveyId(surveyIdx);

        Response response = Response.builder()
                .survey(survey)
                .build();

        questions.forEach(question -> {
            CreateAnswerRequest.AnswerRequest answerRequest = request.getAnswers()
                    .stream().filter(answer -> answer.getIdx() == question.getIdx())
                    .findFirst()
                    .orElseThrow(() -> new ServiceException(ResponseCode.NOT_FOUND_ANSWER));

            String answer = answerRequest.getAnswer();
            if (answer == null) { answer = ""; }
            boolean isRequired = question.isRequired();

            if (isRequired && answer.isEmpty()) {
                throw new ServiceException(ResponseCode.NOT_FOUND_REQUIRED_ANSWER);
            }

            switch (question.getQuestionType()) {
                case SHORT_SENTENCE:
                    if (answer.length() > 255) {
                        throw new ServiceException(ResponseCode.INVALID_SHORT_SENTENCE_ANSWER);
                    }
                    break;
                case SINGLE_CHOICE:
                case MULTI_CHOICE:
                    List<String> options = optionRepository.findAllByQuestionId(question.getIdx()).stream()
                            .map(Option::getName).toList();
                    List<String> optionAnswers = List.of(answer.split(","));
                    optionAnswers.forEach(optionAnswer -> {
                        boolean isValid = options.contains(optionAnswer.trim());
                        if (!isValid) {
                            throw new ServiceException(ResponseCode.INVALID_OPTION);
                        }
                    });
                    break;
            }

            Answer answerEntity = Answer.builder()
                    .question(question)
                    .response(response)
                    .answerText(answer)
                    .build();
            answerRepository.save(answerEntity);
        });

        responseRepository.save(response);
    }
}
