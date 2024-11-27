package com.onboarding.servey.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onboarding.common.exception.BaseException;
import com.onboarding.servey.domain.Servey;
import com.onboarding.servey.dto.response.ServeyResponse;
import com.onboarding.servey.repository.ServeyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServeyServiceImpl implements ServeyService {

	private final ServeyRepository serveyRepository;

	@Transactional(readOnly = true)
	@Override
	public ServeyResponse getServey(Long id) {
		Servey servey = serveyRepository.findById(id).orElseThrow(() -> new BaseException("등록된 설문조사가 없습니다."));
		return ServeyResponse.from(servey);
	}
}
