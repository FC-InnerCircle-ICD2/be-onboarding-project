package com.onboarding.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.onboarding.core", "com.onboarding.survey", "com.onboarding.response"},
    exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
public class ApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

}
