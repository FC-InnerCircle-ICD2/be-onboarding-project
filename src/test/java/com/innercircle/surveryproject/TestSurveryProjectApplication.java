package com.innercircle.surveryproject;

import org.springframework.boot.SpringApplication;

public class TestSurveryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.from(SurveryProjectApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
