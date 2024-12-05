package com.ic2.obd.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ic2.obd.domain.Question;
import com.ic2.obd.domain.Response;
import com.ic2.obd.domain.ResponseAnswer;
import com.ic2.obd.domain.Survey;
import com.ic2.obd.repository.QuestionRepository;
import com.ic2.obd.repository.ResponseRepository;
import com.ic2.obd.repository.SurveyRepository;

@SpringBootTest
@Transactional // 테스트 후 데이터 롤백
public class SurveyResponseTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testSaveSurveyResponse() {
        // 1. 설문조사 및 질문 데이터 준비
        Survey survey = new Survey();
        survey.setSurveyName("고객 피드백 설문조사");
        survey.setSurveyDescription("서비스 품질에 대한 설문조사입니다.");

        Question question1 = new Question();
        question1.setQuestionName("서비스 만족도");
        question1.setQuestionDescription("우리 서비스에 얼마나 만족하십니까?");
        question1.setInputType("단답형");
        question1.setRequired(true);
        question1.setSurvey(survey);

        Question question2 = new Question();
        question2.setQuestionName("개선사항");
        question2.setQuestionDescription("개선해야 할 점은 무엇입니까?");
        question2.setInputType("장문형");
        question2.setRequired(false);
        question2.setSurvey(survey);

        survey.setQuestions(List.of(question1, question2));
        surveyRepository.save(survey); // 설문조사 저장

        // 2. 응답 데이터 생성
        Response response = new Response();
        response.setSurvey(survey); // 응답이 연결된 설문조사 설정

        ResponseAnswer answer1 = new ResponseAnswer();
        answer1.setQuestion(question1); // 질문 연결
        answer1.setAnswer("매우 만족"); // 응답 값 설정
        answer1.setResponse(response);

        ResponseAnswer answer2 = new ResponseAnswer();
        answer2.setQuestion(question2); // 질문 연결
        answer2.setAnswer("고객 서비스가 훌륭합니다."); // 응답 값 설정
        answer2.setResponse(response);

        response.setAnswers(List.of(answer1, answer2));
        responseRepository.save(response); // 응답 저장

        // 3. 저장 확인
        Response savedResponse = responseRepository.findById(response.getId())
                .orElseThrow(() -> new IllegalArgumentException("저장된 응답을 찾을 수 없습니다."));

        Assertions.assertEquals(survey.getId(), savedResponse.getSurvey().getId(), "설문조사 ID가 일치해야 합니다.");
        Assertions.assertEquals(2, savedResponse.getAnswers().size(), "응답 항목 수가 2개여야 합니다.");

        ResponseAnswer savedAnswer1 = savedResponse.getAnswers().get(0);
        Assertions.assertEquals("매우 만족", savedAnswer1.getAnswer(), "첫 번째 질문의 응답이 일치해야 합니다.");

        ResponseAnswer savedAnswer2 = savedResponse.getAnswers().get(1);
        Assertions.assertEquals("고객 서비스가 훌륭합니다.", savedAnswer2.getAnswer(), "두 번째 질문의 응답이 일치해야 합니다.");
    }
}