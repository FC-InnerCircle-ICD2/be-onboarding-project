package com.metsakurr.beonboardingproject.domain.survey.service;

import com.metsakurr.beonboardingproject.domain.survey.dto.RegistSurveyResponse;
import com.metsakurr.beonboardingproject.domain.survey.dto.SurveyRequest;
import com.metsakurr.beonboardingproject.domain.survey.entity.Option;
import com.metsakurr.beonboardingproject.domain.survey.entity.Question;
import com.metsakurr.beonboardingproject.domain.survey.entity.QuestionType;
import com.metsakurr.beonboardingproject.domain.survey.entity.Survey;
import com.metsakurr.beonboardingproject.domain.survey.repository.OptionRepository;
import com.metsakurr.beonboardingproject.domain.survey.repository.QuestionRepository;
import com.metsakurr.beonboardingproject.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final OptionRepository optionRepository;
    private final QuestionRepository questionRepository;

    public RegistSurveyResponse regist(SurveyRequest request) {
        Survey survey = Survey.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        request.getQuestions().forEach(questionRequest -> {
            Question question = Question.builder()
                    .survey(survey)
                    .name(questionRequest.getName())
                    .description(questionRequest.getDescription())
                    .questionType(QuestionType.fromName(questionRequest.getQuestionType()))
                    .isRequired(questionRequest.getIsRequired())
                    .build();
            if (questionRequest.getOptions() != null) {
                questionRequest.getOptions().forEach(optionRequest -> {
                    Option option = Option.builder()
                            .question(question)
                            .name(optionRequest.getName())
                            .build();
//                    optionRepository.save(option);
                    question.addOption(option);
                });
            }
            survey.addQuestion(question);
//            questionRepository.save(question);
        });

        return new RegistSurveyResponse(surveyRepository.save(survey));
    }

}
