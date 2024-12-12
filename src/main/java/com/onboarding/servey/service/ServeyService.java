package com.onboarding.servey.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onboarding.common.exception.BaseException;
import com.onboarding.servey.domain.Answer;
import com.onboarding.servey.domain.AnswerContent;
import com.onboarding.servey.domain.AnswerContentLong;
import com.onboarding.servey.domain.AnswerContentMulti;
import com.onboarding.servey.domain.AnswerContentShort;
import com.onboarding.servey.domain.AnswerContentSingle;
import com.onboarding.servey.domain.Option;
import com.onboarding.servey.domain.Question;
import com.onboarding.servey.domain.QuestionSnapShot;
import com.onboarding.servey.domain.Servey;
import com.onboarding.servey.dto.request.OptionRequest;
import com.onboarding.servey.dto.request.QuestionReplyRequest;
import com.onboarding.servey.dto.request.QuestionRequest;
import com.onboarding.servey.dto.request.ServeyRequest;
import com.onboarding.servey.dto.response.AnswerLongResponse;
import com.onboarding.servey.dto.response.AnswerMultiResponse;
import com.onboarding.servey.dto.response.AnswerResponse;
import com.onboarding.servey.dto.response.AnswerShortResponse;
import com.onboarding.servey.dto.response.AnswerSingleResponse;
import com.onboarding.servey.dto.response.QuestionReplyResponse;
import com.onboarding.servey.dto.response.QuestionResponse;
import com.onboarding.servey.dto.response.ServeyResponse;
import com.onboarding.servey.repository.AnswerRepository;
import com.onboarding.servey.repository.ServeyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServeyService {

	private final ServeyRepository serveyRepository;
	private final AnswerRepository answerRepository;

	@Transactional(readOnly = true)
	public List<QuestionReplyResponse> servey(Pageable pageable, String query) {
		return answerRepository.search(pageable, query).stream()
			.collect(Collectors.groupingBy(Answer::getServeyId))
			.entrySet()
			.stream()
			.map(entry -> {
				Long serveyId = entry.getKey();
				Map<Long, List<Answer>> questionMap = entry.getValue().stream()
					.collect(Collectors.groupingBy(answer -> answer.getQuestionSnapShot().getQuestionId()));

				List<QuestionResponse> questions = questionMap.entrySet()
					.stream()
					.map(questionEntry -> {
						Long questionId = questionEntry.getKey();
						QuestionSnapShot question = questionMap.get(questionId).get(0).getQuestionSnapShot();
						List<AnswerResponse> answers = questionEntry.getValue()
							.stream()
							.map(answer -> {
								switch (answer.getQuestionSnapShot().getType()) {
									case SHORT_TYPE:
										AnswerContentShort answerContentShort = (AnswerContentShort) answer.getContent();
										return AnswerShortResponse.builder()
											.id(answerContentShort.getId())
											.text(answerContentShort.getText()).build();
									case LONG_TYPE:
										AnswerContentLong answerContentLong = (AnswerContentLong) answer.getContent();
										return AnswerLongResponse.builder()
											.id(answerContentLong.getId())
											.text(answerContentLong.getText()).build();
									case SINGLE_LIST:
										AnswerContentSingle answerContentSingle = (AnswerContentSingle) answer.getContent();
										return AnswerSingleResponse.builder()
											.id(answerContentSingle.getId())
											.optionId(answerContentSingle.getOptionId()).build();
									case MULTI_LIST:
										AnswerContentMulti answerContentMulti = (AnswerContentMulti) answer.getContent();
										return AnswerMultiResponse.builder()
											.id(answerContentMulti.getId())
											.optionIds(answerContentMulti.getOptionIds()).build();
									default:
										return null;
								}
							}).collect(Collectors.toList());

						return QuestionResponse.builder()
							.id(questionId)
							.name(question.getName())
							.description(question.getDescription())
							.type(question.getType())
							.required(question.isRequired())
							.answers(answers)
							.build();
					})
					.collect(Collectors.toList());

				return QuestionReplyResponse.builder()
					.serveyId(serveyId)
					.questions(questions)
					.build();
			})
			.collect(Collectors.toList());
	}

	@Transactional
	public void submit(Long serveyId, List<QuestionReplyRequest> questionReplyRequests) {
		Servey servey = getServey(serveyId);
		questionReplyRequests.forEach(questionReplyRequest -> {
			Question question = getQuestion(servey, questionReplyRequest.getId());
			question.validate();
			AnswerContent answerContent = questionReplyRequest.getAnswerRequest().convertToAnswerRequest();
			Answer answer = Answer.builder()
				.serveyId(serveyId)
				.questionSnapShot(question.getSnapShot())
				.content(answerContent)
				.build();
			answer.validate();
			answerRepository.save(answer);
		});
	}

	@Transactional(readOnly = true)
	public ServeyResponse servey(Long serveyId) {
		return ServeyResponse.from(getServey(serveyId));
	}

	@Transactional
	public void create(ServeyRequest serveyRequest) {
		Servey servey = serveyRequest.of();

		serveyRequest.getQuestions().forEach(questionRequest -> {
			Question question = questionRequest.of();
			servey.addQuestion(question);
			questionRequest.getOptions().forEach(optionRequest -> {
				Option option = optionRequest.of();
				question.addOption(option);
			});
		});
		servey.validate();
		serveyRepository.save(servey);
	}

	@Transactional
	public void create(Long serveyId, List<QuestionRequest> questionRequests) {
		questionRequests.forEach(questionRequest -> {
			Servey servey = getServey(serveyId);
			Question question = questionRequest.of();
			servey.addQuestion(question);
			questionRequest.getOptions().forEach(optionRequest -> {
				Option option = optionRequest.of();
				question.addOption(option);
			});
			question.validate();
			serveyRepository.save(servey);
		});
	}

	@Transactional
	public void create(Long serveyId, Long questionId, List<OptionRequest> optionRequests) {
		Servey servey = getServey(serveyId);
		Question question = getQuestion(servey, questionId);
		question.validate();

		optionRequests.forEach(optionRequest -> {
			Option option = optionRequest.of();
			question.addOption(option);
			serveyRepository.save(servey);
		});
	}

	@Transactional
	public void update(Long serveyId, ServeyRequest serveyRequest) {
		Servey servey = getServey(serveyId);
		servey.edit(serveyRequest.getName(), serveyRequest.getDescription());
	}

	@Transactional
	public void update(Long serveyId, Long questionId, QuestionRequest questionRequest) {
		Servey servey = getServey(serveyId);
		Question question = getQuestion(servey, questionId);
		question.edit(
			questionRequest.getName(),
			questionRequest.getDescription(),
			questionRequest.getType(),
			questionRequest.isRequired());
	}

	@Transactional
	public void update(Long serveyId, Long questionId, Long optionId, OptionRequest optionRequest) {
		Servey servey = getServey(serveyId);
		Question question = getQuestion(servey, questionId);
		Option option = getOption(question, optionId);
		option.edit(optionRequest.getNumber());
	}

	@Transactional
	public void delete(Long serveyId) {
		this.delete(serveyId, null, null);
	}

	@Transactional
	public void delete(Long serveyId, Long questionId) {
		this.delete(serveyId, questionId, null);
	}

	@Transactional
	public void delete(Long serveyId, Long questionId, Long optionId) {
		if (questionId == null && optionId == null) {
			Servey servey = getServey(serveyId);;
			serveyRepository.delete(servey);
		} else if (questionId != null && optionId == null) {
			Servey servey = getServey(serveyId);
			Question question = getQuestion(servey, questionId);
			servey.getQuestions().remove(question);
			servey.validate();
		} else {
			Servey servey = getServey(serveyId);
			Question question = getQuestion(servey, questionId);
			Option option = getOption(question, optionId);
			question.getOptions().remove(option);
			question.validate();
		}
	}

	private Servey getServey(Long serveyId) {
		return serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
	}

	private Question getQuestion(Servey servey, Long questionId) {
		return servey.getQuestions().stream()
			.filter(x -> x.getId().equals(questionId))
			.findAny().orElseThrow(() -> new BaseException("설문받을 항목이 없습니다."));
	}

	private Option getOption(Question question, Long optionId) {
		return question.getOptions().stream()
			.filter(x -> x.getId().equals(optionId))
			.findAny().orElseThrow(() -> new BaseException("선택 할 후보가 없습니다."));
	}
}
