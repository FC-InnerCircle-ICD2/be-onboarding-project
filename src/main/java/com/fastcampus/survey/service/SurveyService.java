package com.fastcampus.survey.service;

import com.fastcampus.survey.dto.QuestionDto;
import com.fastcampus.survey.dto.SurveyDto;
import com.fastcampus.survey.entity.Question;
import com.fastcampus.survey.entity.Survey;
import com.fastcampus.survey.repository.QuestionRepository;
import com.fastcampus.survey.repository.SurveyRepository;
import com.fastcampus.survey.util.constant.AnsType;
import com.fastcampus.survey.util.constant.Must;
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

    @Transactional
    public String createSurvey(SurveyDto surveyDto) throws Exception {

        List<QuestionDto> questionDtos = surveyDto.getQuestions();

        List<Question> questions = new ArrayList<>();
        for (QuestionDto question : questionDtos) {
            questions.add(
                Question.builder()
                    .qName(question.getQName())
                    .qDescription(question.getQDescription())
                    .qType(AnsType.fromValue(question.getQType()))
                    .qMust(Must.fromValue(question.getQMust()))
                    .build()
            );
        }

        surveyRepository.save(
            Survey.builder()
                    .name(surveyDto.getName())
                    .description(surveyDto.getDescription())
                    .questions(questions)
                    .build()
        );

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

        // 새로운 질문들 저장
        List<Question> newQuestions = new ArrayList<>();

        for (QuestionDto question : surveyDto.getQuestions()) {
            newQuestions.add(
                Question.builder()
                        .qName(question.getQName())
                        .qDescription(question.getQDescription())
                        .qType(AnsType.fromValue(question.getQType()))
                        .choices(question.getChoices())
                        .qMust(Must.fromValue(question.getQMust()))
                        .build()
            );
        }

        surveyRepository.save(
            Survey.builder()
                    .id(surveyDto.getId())
                    .name(surveyDto.getName())
                    .description(surveyDto.getDescription())
                    .questions(newQuestions)
                    .build()
        );

        return "success";
    }
}
