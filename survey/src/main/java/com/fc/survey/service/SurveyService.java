package com.fc.survey.service;

import com.fc.survey.dto.request.SurveyModDTO;
import com.fc.survey.dto.request.SurveyRegDTO;
import com.fc.survey.entity.Question;
import com.fc.survey.entity.Survey;
import com.fc.survey.repository.QuestionRepository;
import com.fc.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyService {

  private final SurveyRepository surveyRepository;
  private final QuestionRepository questionRepository;

  @Transactional
  public Survey save(SurveyRegDTO requestDTO) {
    Survey survey = requestDTO.toEntity();
    return surveyRepository.save(survey);
  }

  @Transactional
  public Survey update(SurveyModDTO request) {
    Survey survey = surveyRepository.findById(request.getId()).orElseThrow(() ->
        new IllegalArgumentException("Id를 찾을 수 없음"));
    survey.setName(request.getName());
    survey.setDescription(request.getDescription());

    for (Question question : request.toEntity().getQuestions()) {
      if(question.getId() == null) {
        question.setSurvey(survey);
        Question saveQuestion = questionRepository.save(question);
        survey.addQuestion(saveQuestion);
      } else {
        for (Question updateQuestion : survey.getQuestions()) {
          if (updateQuestion.getId().equals(question.getId())) {
            updateQuestion.setName(question.getName());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setSelectValue(question.getSelectValue());
            updateQuestion.setType(question.getType());
            updateQuestion.setDelete(question.isDelete());
            updateQuestion.setRequired(question.isRequired());
            survey.addQuestion(updateQuestion);
            break;
          }
        }
      }
    }
    return surveyRepository.save(survey);
  }
}
