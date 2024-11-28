package com.onboarding.servey.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onboarding.common.exception.BaseException;
import com.onboarding.servey.domain.Option;
import com.onboarding.servey.domain.OptionEditor;
import com.onboarding.servey.domain.Question;
import com.onboarding.servey.domain.QuestionEditor;
import com.onboarding.servey.domain.QuestionType;
import com.onboarding.servey.domain.Servey;
import com.onboarding.servey.domain.ServeyEditor;
import com.onboarding.servey.dto.request.OptionRequest;
import com.onboarding.servey.dto.request.QuestionRequest;
import com.onboarding.servey.dto.request.ServeyRequest;
import com.onboarding.servey.dto.response.ServeyResponse;
import com.onboarding.servey.repository.ServeyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServeyServiceImpl implements ServeyService {

	private final ServeyRepository serveyRepository;
	private final ServeyValidator serveyValidator;

	@Transactional(readOnly = true)
	@Override
	public ServeyResponse getServey(Long id) {
		Servey servey = serveyRepository.findById(id).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
		return ServeyResponse.from(servey);
	}

	@Transactional
	@Override
	public void create(ServeyRequest serveyRequest) {
		Servey servey = Servey.of(serveyRequest);

		serveyRequest.getQuestions().forEach(questionRequest -> {
			serveyValidator.checkTypeAndOptions(questionRequest.getType(), questionRequest.getOptions());
			Question question = Question.of(questionRequest);
			servey.addQuestion(question);
			questionRequest.getOptions().forEach(optionRequest -> {
				Option option = Option.of(optionRequest);
				question.addOption(option);
			});
		});

		serveyRepository.save(servey);
	}

	@Transactional
	@Override
	public void create(Long serveyId, List<QuestionRequest> questionRequests) {
		questionRequests.forEach(questionRequest -> {
			serveyValidator.checkTypeAndOptions(questionRequest.getType(), questionRequest.getOptions());
			Servey servey = serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
			Question question = Question.of(questionRequest);
			servey.addQuestion(question);
			questionRequest.getOptions().forEach(optionRequest -> {
				Option option = Option.of(optionRequest);
				question.addOption(option);
			});
			serveyRepository.save(servey);
		});
	}

	@Transactional
	@Override
	public void create(Long serveyId, Long questionId, List<OptionRequest> optionRequests) {
		Servey servey = serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
		Question question = servey.getQuestions().stream()
			.filter(x -> x.getId().equals(questionId))
			.findAny().orElseThrow(() -> new BaseException("설문받을 항목이 없습니다."));

		serveyValidator.checkTypeAndOptions(question.getType().name());

		optionRequests.forEach(optionRequest -> {
			Option option = Option.of(optionRequest);
			question.addOption(option);

			serveyRepository.save(servey);
		});
	}

	@Transactional
	@Override
	public void update(Long serveyId, ServeyRequest serveyRequest) {
		Servey servey = serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));

		ServeyEditor.ServeyEditorBuilder serveyEditorBuilder = servey.toEditor();
		ServeyEditor serveyEditor = serveyEditorBuilder
			.name(serveyRequest.getName())
			.description(serveyRequest.getDescription())
			.build();

		servey.edit(serveyEditor);
	}

	@Transactional
	@Override
	public void update(Long serveyId, Long questionId, QuestionRequest questionRequest) {
		Servey servey = serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
		Question question = servey.getQuestions().stream()
			.filter(x -> x.getId().equals(questionId))
			.findAny().orElseThrow(() -> new BaseException("설문받을 항목이 없습니다."));

		QuestionEditor.QuestionEditorBuilder questionEditorBuilder = question.toEditor();
		QuestionEditor questionEditor = questionEditorBuilder
			.name(questionRequest.getName())
			.description(questionRequest.getDescription())
			.type(QuestionType.of(questionRequest.getType()))
			.build();

		question.edit(questionEditor);
	}

	@Transactional
	@Override
	public void update(Long serveyId, Long questionId, Long optionId, OptionRequest optionRequest) {
		Servey servey = serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
		Question question = servey.getQuestions().stream()
			.filter(x -> x.getId().equals(questionId))
			.findAny().orElseThrow(() -> new BaseException("설문받을 항목이 없습니다."));
		Option option = question.getOptions().stream()
			.filter(x -> x.getId().equals(optionId))
			.findAny().orElseThrow(() -> new BaseException("선택 할 후보가 없습니다."));

		OptionEditor.OptionEditorBuilder optionEditorBuilder = option.toEditor();
		OptionEditor optionEditor = optionEditorBuilder
			.number(optionRequest.getNumber())
			.build();

		option.edit(optionEditor);
	}

	@Transactional
	@Override
	public void delete(Long serveyId) {
		Servey servey = serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));

		serveyRepository.delete(servey);
	}

	@Transactional
	@Override
	public void delete(Long serveyId, Long questionId) {
		Servey servey = serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
		Question question = servey.getQuestions().stream()
			.filter(x -> x.getId().equals(questionId))
			.findAny().orElseThrow(() -> new BaseException("설문받을 항목이 없습니다."));

		servey.getQuestions().remove(question);

		serveyValidator.checkQuestionSize(servey.getQuestions().size());
	}

	@Transactional
	@Override
	public void delete(Long serveyId, Long questionId, Long optionId) {
		Servey servey = serveyRepository.findById(serveyId).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
		Question question = servey.getQuestions().stream()
			.filter(x -> x.getId().equals(questionId))
			.findAny().orElseThrow(() -> new BaseException("설문받을 항목이 없습니다."));
		Option option = question.getOptions().stream()
			.filter(x -> x.getId().equals(optionId))
			.findAny().orElseThrow(() -> new BaseException("선택 할 후보가 없습니다."));

		question.getOptions().remove(option);

		serveyValidator.checkOptionSize(question.getOptions().size());
	}
}
