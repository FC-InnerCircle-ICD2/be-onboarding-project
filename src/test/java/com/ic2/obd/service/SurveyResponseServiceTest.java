package com.ic2.obd.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ic2.obd.domain.Question;
import com.ic2.obd.domain.Response;
import com.ic2.obd.domain.ResponseAnswer;
import com.ic2.obd.domain.Survey;
import com.ic2.obd.dto.ResponseAnswerDto;
import com.ic2.obd.dto.ResponseDto;
import com.ic2.obd.repository.ResponseRepository;
import com.ic2.obd.repository.SurveyRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SurveyResponseServiceTest {
	@Mock
    private ResponseRepository responseRepository;

    @Mock
    private SurveyRepository surveyRepository;
    
    @InjectMocks
    private SurveyResponseService surveyResponseService;
    
    @Test
    public void testGetFilteredResponses_withFilter() {
    	// 테스트 데이터 준비
        Long surveyId = 1L;
        String questionName = "서비스 만족도";
        String answer = "매우 만족";

        Survey mockSurvey = new Survey();
        mockSurvey.setId(surveyId);
        mockSurvey.setSurveyName("고객 설문");

        Response mockResponse = new Response();
        mockResponse.setId(1L);
        mockResponse.setSurvey(mockSurvey);

        Question mockQuestion = new Question();
        mockQuestion.setId(1L);
        mockQuestion.setQuestionName(questionName);

        ResponseAnswer mockAnswer = new ResponseAnswer();
        mockAnswer.setId(1L);
        mockAnswer.setQuestion(mockQuestion);
        mockAnswer.setAnswer(answer);
        mockResponse.setAnswers(List.of(mockAnswer));

        // Mock 리포지토리 동작 정의
        Mockito.when(surveyRepository.findById(surveyId)).thenReturn(Optional.of(mockSurvey));
        Mockito.when(responseRepository.findBySurveyAndQuestionNameAndAnswer(surveyId, questionName, answer))
                .thenReturn(List.of(mockResponse));

        // 서비스 메서드 호출
        List<ResponseDto> result = surveyResponseService.getResponses(surveyId, questionName, answer);

        // 결과 검증
        Assertions.assertEquals(1, result.size()); // 응답 개수 검증
        ResponseDto responseDto = result.get(0);
        Assertions.assertEquals(surveyId, responseDto.getSurveyId()); // 설문 ID 검증
        Assertions.assertEquals(1, responseDto.getAnswers().size()); // 응답 항목 개수 검증

        ResponseAnswerDto answerDto = responseDto.getAnswers().get(0);
        Assertions.assertEquals(questionName, answerDto.getQuestionName()); // 질문 이름 검증
        Assertions.assertEquals(answer, answerDto.getAnswer()); // 응답 내용 검증
    }
    
    
}
