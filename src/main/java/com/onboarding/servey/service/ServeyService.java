package com.onboarding.servey.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onboarding.common.exception.BaseException;
import com.onboarding.servey.domain.Answer;
import com.onboarding.servey.domain.AnswerContent;
import com.onboarding.servey.domain.Option;
import com.onboarding.servey.domain.Question;
import com.onboarding.servey.domain.Servey;
import com.onboarding.servey.dto.request.OptionRequest;
import com.onboarding.servey.dto.request.QuestionReplyRequest;
import com.onboarding.servey.dto.request.QuestionRequest;
import com.onboarding.servey.dto.request.ServeyRequest;
import com.onboarding.servey.dto.response.ServeyResponse;
import com.onboarding.servey.model.QuestionType;
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
	public Page<ServeyResponse> servey(Pageable pageable, String name) {
		Page<Servey> serveys = serveyRepository.findServeysByNameAndAnswer(pageable, name);
		return serveys.map(ServeyResponse::from);
	}

	@Transactional
	public void submit(Long serveyId, List<QuestionReplyRequest> questionReplyRequests) {
		Servey servey = getServey(serveyId);
		questionReplyRequests.forEach(questionReplyRequest -> {
			Question question = getQuestion(servey, questionReplyRequest.getId());
			AnswerContent answerContent = questionReplyRequest.getAnswerRequest().convertToAnswerRequest();
			Answer answer = Answer.builder()
				.serveyId(serveyId)
				.questionId(question.getId())
				.content(answerContent)
				.build();
			answerRepository.save(answer);
		});
	}

	@Transactional(readOnly = true)
	public ServeyResponse servey(Long serveyId) {
		Servey servey = getServey(serveyId);
		return ServeyResponse.from(servey);
	}

	@Transactional
	public void create(ServeyRequest serveyRequest) {
		Servey servey = serveyRequest.of();
		servey.validate();

		serveyRequest.getQuestions().forEach(questionRequest -> {
			Question question = questionRequest.of();
			servey.addQuestion(question);
			questionRequest.getOptions().forEach(optionRequest -> {
				Option option = optionRequest.of();
				question.addOption(option);
			});
		});
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
			QuestionType.of(questionRequest.getType()),
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
		} else if (questionId == null) {
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
