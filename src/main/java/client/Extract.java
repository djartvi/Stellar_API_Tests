package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class Extract {

    @Step("Extract status code")
    public int responseCode(ValidatableResponse response) {
        return response
                .extract()
                .statusCode();
    }

    @Step("Extract message from JSON")
    public String message(ValidatableResponse response) {
        return response
                .extract()
                .path("message");
    }

    @Step("Extract success from JSON")
    public boolean success(ValidatableResponse response) {
        return response
                .extract()
                .path("success");
    }

    @Step("Extract accessToken")
    public String accessToken(ValidatableResponse response) {
        return response
                .extract()
                .path("accessToken");
    }

    @Step("Extract JSON as object")
    public <T> T jsonObject(ValidatableResponse response, Class<T> object) {
        return response
                .extract()
                .body().as(object);
    }
}
