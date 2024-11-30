package net.gentledot.survey.controller;

import io.micrometer.common.util.StringUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
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

    public static ValidatableResponse submitSurveyAnswer(String surveyId, String requestBody) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/survey/{surveyId}/answer", surveyId)
                .then()
                .log().all();
    }

    public static ValidatableResponse getAllSurveyAnswers(String surveyId, String queryForName, String queryForAnswer) {
        RequestSpecification when = RestAssured.given()
                .accept(ContentType.JSON)
                .when();
        if (StringUtils.isNotBlank(queryForName)) {
            when = when.param("questionName", queryForName);
        }
        if (StringUtils.isNotBlank(queryForAnswer)) {
            when = when.param("answerList", queryForAnswer);
        }

        return when
                .get("/v1/survey/{surveyId}/answer/all", surveyId)
                .then()
                .log().all();
    }


}
