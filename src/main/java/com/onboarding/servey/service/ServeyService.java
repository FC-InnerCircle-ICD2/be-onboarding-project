package com.onboarding.servey.service;

import com.onboarding.servey.dto.request.OptionRequest;
import com.onboarding.servey.dto.request.QuestionRequest;
import com.onboarding.servey.dto.request.ServeyRequest;
import com.onboarding.servey.dto.response.ServeyResponse;

public interface ServeyService {

	ServeyResponse getServey(Long id);
	void create(ServeyRequest serveyRequest);
	void update(Long serveyId, ServeyRequest serveyRequest);
	void update(Long serveyId, Long questionId, QuestionRequest questionRequest);
	void update(Long serveyId, Long questionId, Long optionId, OptionRequest optionRequest);
}
