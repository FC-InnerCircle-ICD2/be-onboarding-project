package net.gentledot.survey.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurveyIntegrations {
    public static ValidatableResponse surveyCreate(String requestBody) {

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(
                        requestBody
                )
                .when()
                .post("/v1/survey")
                .then()
                .log().all();
    }

    public static ValidatableResponse surveyUpdate(String requestBody) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/v1/survey")
                .then()
                .log().all();
    }



}
