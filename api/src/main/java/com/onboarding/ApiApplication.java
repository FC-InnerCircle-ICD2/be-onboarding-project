package com.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.onboarding.api", "com.onboarding.survey", "com.onboarding.response", "com.onboarding.core"})
public class ApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class);
  }
}
