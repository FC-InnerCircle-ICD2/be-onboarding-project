package com.onboarding.response.facade;

import com.onboarding.response.service.create.CreateResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResponseFacade {
  private final CreateResponseService createResponseService;

  public ResponseFacade(CreateResponseService createResponseService) {
    this.createResponseService = createResponseService;
  }
}
