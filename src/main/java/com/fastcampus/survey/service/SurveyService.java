package com.fastcampus.survey.service;

import com.fastcampus.survey.dto.*;
import com.fastcampus.survey.entity.Question;
import com.fastcampus.survey.entity.Survey;
import com.fastcampus.survey.entity.SurveyAnswer;
import com.fastcampus.survey.repository.QuestionRepository;
import com.fastcampus.survey.repository.SurveyAnswerRepository;
import com.fastcampus.survey.repository.SurveyRepository;
import com.fastcampus.survey.util.constant.AnsType;
import com.fastcampus.survey.util.constant.Must;
import com.fastcampus.survey.util.json.JsonUtil;
import com.fastcampus.survey.util.mapper.SurveyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SurveyAnswerRepository surveyAnsRepository;

    @Transactional
    public String createSurvey(SurveyDto surveyDto) throws Exception {
        surveyRepository.save(SurveyMapper.toEntity(surveyDto));
        return "Success";
    }

    @Transactional
    public String updateSurvey(SurveyDto surveyDto) throws Exception {

        // 이전에 맵핑된 질문들 삭제
        Survey survey = surveyRepository.findById(surveyDto.getId()).orElseThrow(() -> new Exception("Exception occured"));

        List<Question> oldQuestions = survey.getQuestions();
        for (Question oldQuestion : oldQuestions) {
            questionRepository.delete(oldQuestion);
        }

        surveyRepository.save(SurveyMapper.toEntity(surveyDto));

        return "success";
    }

    @Transactional
    public String submitSurvey(List<SurveyAnswerDto> surveyAnswerDtoList) throws Exception {
        Survey survey = surveyRepository.findById(surveyAnswerDtoList.get(0).getSurvey_id()).orElseThrow(() -> new Exception("잘못된 설문조사 ID"));

        for (SurveyAnswerDto surveyAnswerDto : surveyAnswerDtoList) {
            Question question = questionRepository.findById(surveyAnswerDto.getQuestion_id()).orElseThrow(() -> new Exception("잘못된 설문조사 항목 ID"));
            surveyAnsRepository.save(
                SurveyAnswer.builder()
                        .survey(survey)
                        .question(question)
                        .surveyContent(JsonUtil.survey2Json(survey))
                        .writer(surveyAnswerDto.getWriter())
                        .answer(surveyAnswerDto.getAnswer())
                        .build()
            );
        }

        return "Success";
    }

    @Transactional
    public SurveyAnswerDetailDto getSurveyAnswers(Long surveyID) {
        List<SurveyAnswer> surveyAnswers = surveyAnsRepository.findBySurveyId(surveyID).orElse(new ArrayList<>());

        List<QuestionAnsDto> questionAnsList = new ArrayList<>();
        for (SurveyAnswer surveyAnswer : surveyAnswers) {
            Question tmpQuestion = surveyAnswer.getQuestion();
            questionAnsList.add(
                QuestionAnsDto.builder()
                        .questionID(surveyAnswer.getQuestion().getId())
                        .qName(tmpQuestion.getQName())
                        .qDesc(tmpQuestion.getQDescription())
                        .writer(surveyAnswer.getWriter())
                        .qAns(surveyAnswer.getAnswer())
                        .build()
            );
        }

        Survey tmpSurvey = surveyAnswers.get(0).getSurvey();

        return SurveyAnswerDetailDto.builder()
                .surveyID(tmpSurvey.getId())
                .surveyName(tmpSurvey.getName())
                .surveyDesc(tmpSurvey.getDescription())
                .questionAnsList(questionAnsList)
                .build();
    }
}
