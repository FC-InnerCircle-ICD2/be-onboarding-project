package com.ic2.obd.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ic2.obd.domain.Question;
import com.ic2.obd.domain.Survey;
import com.ic2.obd.repository.QuestionRepository;
import com.ic2.obd.repository.SurveyRepository;

@SpringBootTest
@Transactional // 테스트 후 롤백
public class SurveyInsertTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testInsertSurvey() {
        // 1. 설문조사 데이터 생성
        Survey survey = new Survey();
        survey.setSurveyName("고객 피드백 설문조사");
        survey.setSurveyDescription("서비스 품질에 대한 설문조사입니다.");

        // 2. 질문 데이터 생성
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

        // 설문조사에 질문 추가
        survey.setQuestions(List.of(question1, question2));

        // 3. 설문조사 저장
        surveyRepository.save(survey);

        // 4. 저장 확인
        Survey savedSurvey = surveyRepository.findById(survey.getId())
                .orElseThrow(() -> new IllegalArgumentException("저장된 설문조사를 찾을 수 없습니다."));

        Assertions.assertEquals("고객 피드백 설문조사", savedSurvey.getSurveyName());
        Assertions.assertEquals(2, savedSurvey.getQuestions().size());

        Question savedQuestion1 = savedSurvey.getQuestions().get(0);
        Assertions.assertEquals("서비스 만족도", savedQuestion1.getQuestionName());
        Assertions.assertEquals("단답형", savedQuestion1.getInputType());

        Question savedQuestion2 = savedSurvey.getQuestions().get(1);
        Assertions.assertEquals("개선사항", savedQuestion2.getQuestionName());
        Assertions.assertEquals("장문형", savedQuestion2.getInputType());
    }
}
