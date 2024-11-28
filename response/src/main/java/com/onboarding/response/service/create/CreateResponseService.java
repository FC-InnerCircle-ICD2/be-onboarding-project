package com.onboarding.response.service.create;

import com.onboarding.response.entity.Response;
import com.onboarding.response.repository.ResponseRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateResponseService {
  private final ResponseRepository responseRepository;

  public CreateResponseService(ResponseRepository responseRepository) {
    this.responseRepository = responseRepository;
  }

  public void createResponse(Response response) {
    responseRepository.save(response);
  }
}
