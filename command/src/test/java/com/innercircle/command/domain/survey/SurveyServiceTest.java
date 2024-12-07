package com.innercircle.command.domain.survey;

import static org.assertj.core.api.Assertions.assertThat;

import com.innercircle.command.application.survey.question.LongTextQuestionInput;
import com.innercircle.command.application.survey.question.MultipleChoiceQuestionInput;
import com.innercircle.command.application.survey.question.ShortTextQuestionInput;
import com.innercircle.command.application.survey.question.SingleChoiceQuestionInput;
import com.innercircle.command.domain.survey.question.QuestionRepository;
import com.innercircle.common.domain.survey.question.QuestionType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SurveyServiceTest {

	@Spy
	private QuestionRepository questionRepository;
	@Spy
	private SurveyRepository surveyRepository;
	@InjectMocks
	private SurveyService surveyService;

	@Test
	void createSurveySuccessTest() {
		var surveyId = "surveyId";
		var surveyName = "name";
		var surveyDescription = "description";
		var questionInputs = List.of(
				new ShortTextQuestionInput(QuestionType.SHORT_TEXT, true, "SHORT_TEXT", "description"),
				new LongTextQuestionInput(QuestionType.LONG_TEXT, true, "LONG_TEXT", "description"),
				new SingleChoiceQuestionInput(QuestionType.SINGLE_CHOICE, true, "SINGLE_CHOICE", "description", List.of("option1", "option2")),
				new MultipleChoiceQuestionInput(QuestionType.MULTIPLE_CHOICE, true, "MULTIPLE_CHOICE", "description", List.of("option1", "option2", "option3"))
		);

		var mockSurvey = new Survey(surveyId, surveyName, surveyDescription);

		Mockito.when(surveyRepository.generateId()).thenReturn("surveyId");
		Mockito.when(surveyRepository.save(Mockito.any(Survey.class))).thenReturn(mockSurvey);
		Mockito.when(questionRepository.generateId()).thenReturn("questionId-1", "questionId-2", "questionId-3", "questionId-4");

		var surveyIdentifier = surveyService.createSurvey(surveyName, surveyDescription, questionInputs);

		assertThat(surveyIdentifier.getId()).isEqualTo(surveyId);
		Mockito.verify(surveyRepository, Mockito.times(1)).save(Mockito.any(Survey.class));
	}
}