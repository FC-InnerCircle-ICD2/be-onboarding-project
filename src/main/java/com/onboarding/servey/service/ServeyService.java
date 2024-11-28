package com.onboarding.servey.service;

import java.util.List;

import com.onboarding.servey.dto.request.OptionRequest;
import com.onboarding.servey.dto.request.QuestionRequest;
import com.onboarding.servey.dto.request.ServeyRequest;
import com.onboarding.servey.dto.response.ServeyResponse;

public interface ServeyService {

	ServeyResponse getServey(Long id);
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
