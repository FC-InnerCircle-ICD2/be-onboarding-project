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
//            Question savedQuestion = questionRepository.save(
//                    Question.builder()
//                            .qName(question.getQName())
//                            .qDescription(question.getQDescription())
//                            .qType(AnsType.fromValue(question.getQType()))
//                            .qMust(Must.fromValue(question.getQMust()))
//                            .build()
//            );
//            questions.add(savedQuestion);
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
}
