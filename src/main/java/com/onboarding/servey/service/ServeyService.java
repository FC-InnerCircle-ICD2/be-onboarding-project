package com.onboarding.servey.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.onboarding.servey.dto.request.AnswerRequest;
import com.onboarding.servey.dto.request.OptionRequest;
import com.onboarding.servey.dto.request.QuestionRequest;
import com.onboarding.servey.dto.request.ServeyRequest;
import com.onboarding.servey.dto.response.ServeyResponse;

public interface ServeyService {

	ServeyResponse servey(Long id);
	Page<ServeyResponse> servey(Pageable pageable, String name, String answer);
	void submit(Long serveyId, List<AnswerRequest> answerRequests);
	void create(ServeyRequest serveyRequest);
	void create(Long serveyId, List<QuestionRequest> questionRequests);
	void create(Long serveyId, Long questionId, List<OptionRequest> optionRequests);
	void update(Long serveyId, ServeyRequest serveyRequest);
	void update(Long serveyId, Long questionId, QuestionRequest questionRequest);
	void update(Long serveyId, Long questionId, Long optionId, OptionRequest optionRequest);
	void delete(Long serveyId);
	void delete(Long serveyId, Long questionId);
	void delete(Long serveyId, Long questionId, Long optionId);
}
