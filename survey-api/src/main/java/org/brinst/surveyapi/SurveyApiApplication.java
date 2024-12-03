package org.brinst.surveyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.brinst.surveycore", "org.brinst.surveycommon", "org.brinst.surveyapi"})
public class SurveyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyApiApplication.class, args);
	}

}
